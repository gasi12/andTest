package com.example.andtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.andtest.api.service.AuthService
import com.example.andtest.navigation.Navigation
import com.example.andtest.ui.theme.AndTestTheme


class MainActivity : ComponentActivity() {
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val storage = SecurePreferences.getInstance(this)
        Log.i("storage on boot",storage.getToken(SecurePreferences.TokenType.REFRESH).toString())
        authService = AuthService(applicationContext)


        setContent {
            AndTestTheme {
                Navigation(authService)
            }
        }
    }
}