package com.example.andtest.api

import android.content.Context
import androidx.navigation.NavController

import com.example.andtest.SecurePreferences
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL ="http://192.168.1.87:8080/"

object RetrofitClient {
    fun getClient(context: Context, tokenAuthenticator: Authenticator): Retrofit {
        val securePreferences = SecurePreferences.getInstance(context)
        val authInterceptor = AuthInterceptor(securePreferences)

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
