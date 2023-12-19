package com.example.andtest.api

import android.content.Context

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL ="http://192.168.1.87:8080/"

object RetrofitClient {
    fun getClient(context: Context): Retrofit {



        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor(context))
            .authenticator(TokenAuthenticator(context))
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}
