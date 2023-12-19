package com.example.andtest.api.service

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto

class MockService :ServiceInterface{
    override fun loginCall(body: LoginBody, callback: (LoginResponse?, Boolean) -> Unit) {
        // Simulate a successful response
        val loginResponse = LoginResponse("mockAuthToken", "mockRefreshToken","mockusername","mockfirstname","mocklastname")
        callback(loginResponse, true)
    }

 override fun refreshToken(body: RefreshTokenBody, callback: (LoginResponse?, Boolean) -> Unit) {
        // Simulate a successful token refresh
     val loginResponse = LoginResponse("mockAuthToken", "mockRefreshToken","mockusername","mockfirstname","mocklastname")
        callback(loginResponse, true)
    }

    override fun getAllServiceRequestsWithUserName(
        pageNo: Int?,
        pageSize: Int?,
        callback: (List<ServiceRequestWithDetailsDto>) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}
