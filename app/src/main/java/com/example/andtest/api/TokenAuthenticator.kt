package com.example.andtest.api

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.andtest.SecurePreferences
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.navigation.Screen
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException
class TokenAuthenticator(private val securePreferences: SecurePreferences, private val authService: AuthService) : Authenticator {



    override fun authenticate(route: Route?, response: Response): Request? {
        var token = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)

        if (token == null) {

            return null
        }
        val tokenBody = RefreshTokenBody(token)
        val oldRequest = response.request()
        var newRequest : Request? =null
        authService.refreshToken(tokenBody) { tokens, isSuccess ->
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
