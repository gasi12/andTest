package com.example.andtest.api.service

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.Tokens

interface ServiceInterface {
    fun successfulresponse(body: LoginBody, callback: (Tokens?, Boolean) -> Unit)
    fun refreshToken(body: RefreshTokenBody, callback: (Tokens?, Boolean) -> Unit)
}