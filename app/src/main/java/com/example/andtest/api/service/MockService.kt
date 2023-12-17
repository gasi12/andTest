package com.example.andtest.api.service

import android.util.Log
import com.example.andtest.SecurePreferences
import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.Tokens
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MockService :ServiceInterface{
    override fun successfulresponse(body: LoginBody, callback: (Tokens?, Boolean) -> Unit) {
        // Simulate a successful response
        val tokens = Tokens("mockAuthToken", "mockRefreshToken")
        callback(tokens, true)
    }

 override fun refreshToken(body: RefreshTokenBody, callback: (Tokens?, Boolean) -> Unit) {
        // Simulate a successful token refresh
        val tokens = Tokens("newMockAuthToken", "newMockRefreshToken")
        callback(tokens, true)
    }

    }
