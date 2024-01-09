package com.example.andtest.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


//
//const val BASE_URL ="http://192.168.1.87:8080/" //pi
const val BASE_URL ="http://100.72.70.142:8080/" //vpn
//const val BASE_URL ="http://192.168.1.108:8080/" //local
object RetrofitClient {
    fun getClient(context: Context): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val gson1 = GsonBuilder().registerTypeAdapter(
            LocalDateTime::class.java,
            JsonDeserializer { json, _, _ ->
                LocalDateTime.parse(json.asJsonPrimitive.asString)
            }
        ).create()

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor(context))
            .authenticator(TokenAuthenticator(context))
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson1))
            .build()

    }
}

