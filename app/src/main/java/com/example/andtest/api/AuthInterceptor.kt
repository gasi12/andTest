package com.example.andtest.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.andtest.SecurePreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    private val securePreferences = SecurePreferences.getInstance(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        Log.i("AuthInterceptor","original request ${original.url()}")
        // Check if the request is for the refresh endpoint
        if (original.url().encodedPath().endsWith("/auth/refresh")) {
            Log.i("AuthInterceptor","returning auth refresh")
            return chain.proceed(original)
        }

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
//class AuthInterceptor(private val context: Context) : Interceptor {
//    private val securePreferences = SecurePreferences.getInstance(context)
//    private val token = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val originalRequest = chain.request()
//
//        var response = chain.proceed(originalRequest)
//        Log.i("authintercepor","response code ${response.code()}")
//        if (response.code() == 401) {
//            if (token != null) {
//                val newRequest = originalRequest.newBuilder()
//                    .header("Authorization", "Bearer $token")
//                    .build()
//                return chain.proceed(newRequest)
//            }
//        }else{
////            restartApp(context)
//        }
//
//        return response
//    }
//    private fun restartApp(context: Context) {
//        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
//        intent?.let {
//            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            context.startActivity(it)
//        }
//    }
//}