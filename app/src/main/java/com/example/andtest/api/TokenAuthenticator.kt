package com.example.andtest.api

import android.content.Context
import android.util.Log

import com.example.andtest.SecurePreferences
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.service.AuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val context: Context) : Authenticator {
    private val securePreferences = SecurePreferences.getInstance(context)

    override fun authenticate(route: Route?, response: Response): Request? {

        val authService = AuthService.getInstance(context)
        val refreshToken = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)?:""
        authService.refreshTokenSynchronously(RefreshTokenRequest(refreshToken))
        val newToken = securePreferences.getToken(SecurePreferences.TokenType.AUTH)
        return response.request().newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }


}

//class TokenAuthenticator(private val context: Context) : Authenticator {
//    private val securePreferences = SecurePreferences.getInstance(context)
//
//    override fun authenticate(route: Route?, response: Response): Request? {
//        Log.i("TokenAuthenticator","response code ${response.code()}")
//        // Check if the response indicates that the token has expired
//        if (response.code() == 401) {
//            Log.i("TokenAuthenticator","response code ${response.code()}")
//            val refreshToken = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)
//            var newAccessToken: String? = null
//            refreshToken?.let {
//                // Use the refresh token to get a new access token
//                val authService = AuthService.getInstance(context)
//
//                authService.refreshToken(RefreshTokenRequest(refreshToken)) { loginResponse, isSuccess ->
//                    if (isSuccess) {
//
//                        newAccessToken = loginResponse?.token
//                        Log.i("TokenAuthenticator","new access token $newAccessToken")
//                    }
//                }
//
//                // If a new access token was obtained, retry the original request with the new token
//
//            }
//            newAccessToken?.let { token ->
//                Log.i("TokenAuthenticator","original request new token")
//                return response.request().newBuilder()
//                    .header("Authorization", "Bearer $token")
//                    .build()
//            }
//        }
//        // Return null if the token could not be refreshed or if the response code is not 401
//        return null
//    }
//}