package com.example.andtest.api

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.Tokens
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {


    @POST("/auth/authenticate/user")
    fun login(@Body body: LoginBody): Call<Tokens>


    @POST("/auth/refresh")
    fun refreshToken(@Body body: RefreshTokenBody): Call<Tokens>


}


