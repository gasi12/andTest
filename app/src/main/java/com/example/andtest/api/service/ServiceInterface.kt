package com.example.andtest.api.service

import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path

interface ServiceInterface {
    fun loginCall(body: LoginRequest, callback: (LoginResponse?, Boolean) -> Unit)
    fun refreshToken(body: RefreshTokenRequest, callback: (LoginResponse?, Boolean) -> Unit)

    fun getAllServiceRequestsWithUserName(pageNo: Int?,pageSize: Int?, callback: (List<ServiceRequestWithUserNameDtoResponse>) -> Unit)

    fun getServiceDetails(id:Long, callback: (ServiceRequestWithDetailsDto) -> Unit)

    fun deleteServiceById(id:Long,callback:(Boolean)-> Unit)

    fun addStatusToService(serviceId : Long, body: StatusHistoryDtoRequest,callback: (StatusHistoryDtoRequest?,Boolean) -> Unit)
}