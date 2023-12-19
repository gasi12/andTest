package com.example.andtest.api.service

import android.content.Context
import android.util.Log
import com.example.andtest.SecurePreferences
import com.example.andtest.api.MyApi
import com.example.andtest.api.RetrofitClient
import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService(context: Context) : ServiceInterface {


    val authApi = RetrofitClient.getClient(context).create(MyApi::class.java)
    val myStorage = SecurePreferences.getInstance(context)
    override fun loginCall(
        body: LoginBody,
        callback: (loginResponse: LoginResponse?, success: Boolean) -> Unit
    ) {
        // Implement the logic to make the API call and handle the response
        authApi.login(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val tokens = response.body()
                    if (tokens != null) {
                        Log.i("token", "${tokens.token}")
                        myStorage.saveToken(tokens.token, SecurePreferences.TokenType.AUTH)
                        myStorage.saveToken(
                            tokens.refreshToken,
                            SecurePreferences.TokenType.REFRESH
                        )
                        myStorage.saveAnything("username", tokens.username)
                        myStorage.saveAnything("firstname", tokens.firstname)
                        myStorage.saveAnything("lastname", tokens.lastname)
                    }
                    callback(tokens, true)
                } else {
                    Log.i("succesfulresposne", "res fail")
                    Log.i(
                        "succesfulresposne",
                        "${myStorage.getToken(SecurePreferences.TokenType.AUTH)}"
                    )
                    callback(null, false)
                    myStorage.clearTokens()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                myStorage.clearTokens()
                Log.i("request failed", "${t.message}")
                callback(null, false)
            }
        })
    }

    override fun refreshToken(
        body: RefreshTokenBody,
        callback: (LoginResponse?, Boolean) -> Unit
    ) {
        Log.i("im sendig token: ", body.token)
        authApi.refreshToken(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val tokens = response.body()
                    tokens?.let { myStorage.saveAnything("username", it.username) }
                    tokens?.let { myStorage.saveAnything("firstname", it.firstname) }
                    tokens?.let { myStorage.saveAnything("lastname", it.lastname) }
                    Log.i("tokeny authservice.refreshtoken", tokens.toString())
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
                    Log.i("debugtoken", tokens?.refreshToken.toString())
                    if (tokens?.token == null || tokens?.refreshToken == null) {
                        myStorage.clearTokens()
                    }
                    callback(tokens, true)
                } else {
                    callback(null, false)

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("request failed", "${t.message}")
                // myStorage.clearTokens()
                callback(null, false)
            }
        })

    }

    override fun getAllServiceRequestsWithUserName(
        pageNo: Int?,
        pageSize: Int?,
        callback: (List<ServiceRequestWithDetailsDto>) -> Unit
    ){
        val serviceRequestList: MutableList<ServiceRequestWithDetailsDto> = mutableListOf()
        authApi.getAllServiceRequestsWithUserName(pageNo, pageSize)
            .enqueue(object : Callback<List<ServiceRequestWithDetailsDto>> {

                override fun onResponse(
                    call: Call<List<ServiceRequestWithDetailsDto>>,
                    response: Response<List<ServiceRequestWithDetailsDto>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            serviceRequestList.addAll(body)
                        }

                        callback(serviceRequestList)
                    }
                }

                override fun onFailure(
                    call: Call<List<ServiceRequestWithDetailsDto>>,
                    t: Throwable
                ) {
                    Log.i("onFail->getAllServiceRequestsWithUserName","failed with reason ${t.message}")
                }
            }


            )

    }
}