package com.example.andtest.api

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyApi {


    @POST("/auth/authenticate/user")
    fun login(@Body body: LoginBody): Call<LoginResponse>


    @POST("/auth/refresh")
    fun refreshToken(@Body body: RefreshTokenBody): Call<LoginResponse>

    @GET("/services/service-requests-with-user-name")
    fun getAllServiceRequestsWithUserName(
        @Query("pageNo") pageNo: Int? = 0,
        @Query("pageSize")pageSize: Int? = 10
    ): Call<List<ServiceRequestWithDetailsDto>>


}


