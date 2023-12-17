package com.example.andtest.api

import android.content.Context

import com.example.andtest.SecurePreferences
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.service.AuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(context: Context) : Authenticator {


private val securePreferences= SecurePreferences.getInstance(context)
    private val context= context
    override fun authenticate(route: Route?, response: Response): Request? {
        var token = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)

        if (token == null) {

            return null
        }
        val tokenBody = RefreshTokenBody(token)
        val oldRequest = response.request()
        var newRequest : Request? =null
       AuthService(context = context).refreshToken(tokenBody) { tokens, isSuccess ->
            if(isSuccess)
            {
                token = securePreferences.getToken(SecurePreferences.TokenType.AUTH)
                if(token!=null){
                    newRequest = oldRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                }
            }

    }

        return newRequest
    }

}
