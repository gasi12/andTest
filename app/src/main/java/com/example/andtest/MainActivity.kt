package com.example.andtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.andtest.api.service.AuthService
import com.example.andtest.navigation.Navigation

import com.example.andtest.ui.theme.AppTheme
import java.lang.reflect.Modifier


class MainActivity : ComponentActivity() {
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
         val storage = SecurePreferences.getInstance(this)
        Log.i("storage on boot",storage.getToken(SecurePreferences.TokenType.REFRESH).toString())
        authService = AuthService(applicationContext)


        setContent {
            AppTheme {

                    Navigation(authService = authService)

            }
        }
    }
}