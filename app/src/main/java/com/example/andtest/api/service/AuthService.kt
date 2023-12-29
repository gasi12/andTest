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
import com.example.andtest.api.dto.ServiceRequestWithUserNameDto
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
                        Log.i("response body", response.body().toString())
                        myStorage.saveToken(tokens.token, SecurePreferences.TokenType.AUTH)
                        myStorage.saveToken(
                            tokens.refreshToken,
                            SecurePreferences.TokenType.REFRESH
                        )
                        myStorage.saveAnything("username", tokens.username)
                        myStorage.saveAnything("firstName", tokens.firstName)
                        myStorage.saveAnything("lastName", tokens.lastName)
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
        Log.i("im sendig token: ", body.refreshToken)
        authApi.refreshToken(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful&&response.body()?.token!=null) {
                    val tokens = response.body()
                    Log.i("tokeny authservice.refreshtoken", response.body().toString())
                    tokens?.let { myStorage.saveAnything("username", it.username) }
                    tokens?.let { myStorage.saveAnything("firstName", it.firstName) }
                    tokens?.let { myStorage.saveAnything("lastName", it.lastName) }
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
        callback: (List<ServiceRequestWithUserNameDto>) -> Unit
    ){
        val serviceRequestList: MutableList<ServiceRequestWithUserNameDto> = mutableListOf()
        authApi.getAllServiceRequestsWithUserName(pageNo, pageSize)
            .enqueue(object : Callback<List<ServiceRequestWithUserNameDto>> {

                override fun onResponse(
                    call: Call<List<ServiceRequestWithUserNameDto>>,
                    response: Response<List<ServiceRequestWithUserNameDto>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            serviceRequestList.addAll(body)
                        }

                        callback(serviceRequestList)
                    }
                }

                override fun onFailure(
                    call: Call<List<ServiceRequestWithUserNameDto>>,
                    t: Throwable
                ) {
                    Log.i("onFail->getAllServiceRequestsWithUserName","failed with reason ${t.message}")
                }
            }


            )

    }

   override fun getServiceDetails(id: Long, callback: (ServiceRequestWithDetailsDto) -> Unit) {
Log.i("getservicedetails","id: $id")

        authApi.getServiceDetails(id)
            .enqueue(object : Callback<ServiceRequestWithDetailsDto> {
                override fun onResponse(
                    call: Call<ServiceRequestWithDetailsDto>,
                    response: Response<ServiceRequestWithDetailsDto>
                ) {
                    Log.i("getservicedetails","we did it into onresponse")
                    if (response.isSuccessful) {
                        Log.i("getservicedetails","response success and ${response.body().toString()}")
                        response.body()?.let { body ->
                            callback(body)
                        }

                    }
                    Log.i("getservicedetails","but we didnt catch response")
                    Log.i("getservicedetails","zwrotka : ${response.body().toString()}")
                }

                override fun onFailure(
                    call: Call<ServiceRequestWithDetailsDto>,
                    t: Throwable
                ) {
                    Log.i("onFail->getServiceDetails","failed with reason ${t.message}")
                }
            })
    }

    override fun deleteServiceById(id: Long, callback: (Boolean) -> Unit) {
        authApi.deleteServiceById(id)
            .enqueue(object : Callback<Boolean>
            {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    callback(response.isSuccessful)
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.i("onFail->deleteServiceById","failed with reason ${t.message}")
                }
            }

            )
    }
}