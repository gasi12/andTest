package com.example.andtest.api

import com.example.andtest.SecurePreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val securePreferences: SecurePreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = securePreferences.getToken(SecurePreferences.TokenType.AUTH)
        if (token != null) {
            val request = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

            return chain.proceed(request)
        }

        return chain.proceed(original)
    }
}
