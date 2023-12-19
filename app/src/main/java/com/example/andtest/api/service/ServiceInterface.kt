package com.example.andtest.api.service

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto

interface ServiceInterface {
    fun loginCall(body: LoginBody, callback: (LoginResponse?, Boolean) -> Unit)
    fun refreshToken(body: RefreshTokenBody, callback: (LoginResponse?, Boolean) -> Unit)

    fun getAllServiceRequestsWithUserName(pageNo: Int?,pageSize: Int?, callback: (List<ServiceRequestWithDetailsDto>) -> Unit)
}