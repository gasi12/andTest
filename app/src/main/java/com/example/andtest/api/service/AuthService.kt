package com.example.andtest.api.service

import android.content.Context
import android.util.Log
import com.example.andtest.SecurePreferences
import com.example.andtest.api.MyApi
import com.example.andtest.api.RetrofitClient
import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.Tokens
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService(context: Context) :ServiceInterface{


val authApi = RetrofitClient.getClient(context).create(MyApi::class.java)
    val myStorage = SecurePreferences.getInstance(context)
    override fun successfulresponse(body: LoginBody, callback: (tokens: Tokens?, success: Boolean) -> Unit) {
        // Implement the logic to make the API call and handle the response
        authApi.login(body).enqueue(object : Callback<Tokens> {
            override fun onResponse(call: Call<Tokens>, response: Response<Tokens>) {
                if (response.isSuccessful) {
                    val tokens = response.body()
                    if (tokens != null) {
                        Log.i("token","${tokens.token}")
                        myStorage.saveToken(tokens.token, SecurePreferences.TokenType.AUTH)
                        myStorage.saveToken(tokens.refreshToken, SecurePreferences.TokenType.REFRESH)
                    }
                    callback(tokens, true)
                } else {
                    Log.i("succesfulresposne", "res fail")
                    Log.i("succesfulresposne", "${myStorage.getToken(SecurePreferences.TokenType.AUTH)}")
                    callback(null, false)
                    myStorage.clearTokens()
                }
            }

            override fun onFailure(call: Call<Tokens>, t: Throwable) {
                myStorage.clearTokens()
                Log.i("request failed", "${t.message}")
                callback(null, false)
            }
        })
    }

    override fun refreshToken(body: RefreshTokenBody, callback: (Tokens?, Boolean) -> Unit) {
        authApi.refreshToken(body).enqueue(object : Callback<Tokens> {
            override fun onResponse(call: Call<Tokens>, response: Response<Tokens>) {
                if (response.isSuccessful) {
                    val tokens = response.body()
                    tokens?.token?.let {
                        myStorage.saveToken(
                            it,
                            SecurePreferences.TokenType.AUTH
                        )
                    }
                    tokens?.refreshToken?.let {
                        myStorage.saveToken(
                            it,
                            SecurePreferences.TokenType.REFRESH
                        )
                    }
                    if(tokens?.token == null||tokens?.refreshToken== null){
                        myStorage.clearTokens()
                        navigateToLoginScreen()
                    }
                    callback(tokens, true)
                } else {
                    callback(null, false)

                }
            }

            override fun onFailure(call: Call<Tokens>, t: Throwable) {
                Log.i("request failed", "${t.message}")
                myStorage.clearTokens()
                navigateToLoginScreen()
                callback(null, false)
            }
        })

    }

    private fun navigateToLoginScreen() {
        //todo to be put in viewmodel
//        // Use the passed NavController to navigate
//        navController.navigate(Screen.LOGIN.name) {
//            // Clear back stack to prevent going back to the previous screen
//            popUpTo(navController.graph.id) {
//                inclusive = true
//            }
//            // Optional: Avoid multiple copies of the same destination
//            launchSingleTop = true
//        }
    }
}