package com.example.andtest.api

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.andtest.SecurePreferences
import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.Tokens
import com.example.andtest.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService(private val context: Context, private val navController: NavController) {
    private val securePreferences = SecurePreferences.getInstance(context)
    private val tokenAuthenticator = TokenAuthenticator(securePreferences, this)
    private val retrofit = RetrofitClient.getClient(context, tokenAuthenticator)
    private val authApi = retrofit.create(MyApi::class.java)

    private val myStorage = SecurePreferences.getInstance(context)


    fun successfulresponse(body: LoginBody, callback: (Tokens?, Boolean) -> Unit) {
        Log.i("succesfulresposne", "begin")
        authApi.login(body).enqueue(object : Callback<Tokens> {

            override fun onResponse(call: Call<Tokens>, response: Response<Tokens>) {
                if (response.isSuccessful) {
                    Log.i("succesfulresposne", "res suc")
                    val tokens = response.body()
                    tokens?.token?.let { myStorage.saveToken(it, SecurePreferences.TokenType.AUTH) }
                    tokens?.token?.let {
                        myStorage.saveToken(
                            it,
                            SecurePreferences.TokenType.REFRESH
                        )
                    }
                    callback(tokens, true)
                } else {
                    Log.i("succesfulresposne", "res fail")
                    callback(null, false)
                }
            }

            override fun onFailure(call: Call<Tokens>, t: Throwable) {
                Log.i("request failed", "${t.message}")
                callback(null, false)
            }
        })
    }

    fun refreshToken(body: RefreshTokenBody, callback: (Tokens?, Boolean) -> Unit) {
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
        // Use the passed NavController to navigate
        navController.navigate(Screen.LOGIN.name) {
            // Clear back stack to prevent going back to the previous screen
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            // Optional: Avoid multiple copies of the same destination
            launchSingleTop = true
        }
    }
}