package com.example.andtest.api.service

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDto

interface ServiceInterface {
    fun loginCall(body: LoginBody, callback: (LoginResponse?, Boolean) -> Unit)
    fun refreshToken(body: RefreshTokenBody, callback: (LoginResponse?, Boolean) -> Unit)

    fun getAllServiceRequestsWithUserName(pageNo: Int?,pageSize: Int?, callback: (List<ServiceRequestWithUserNameDto>) -> Unit)

    fun getServiceDetails(id:Long, callback: (ServiceRequestWithDetailsDto) -> Unit)

    fun deleteServiceById(id:Long,callback:(Boolean)-> Unit)
}