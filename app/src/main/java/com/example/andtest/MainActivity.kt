package com.example.andtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.andtest.api.service.AuthService
import com.example.andtest.navigation.Navigation
import com.example.andtest.navigation.Screen
import com.example.andtest.ui.theme.AndTestTheme


class MainActivity : ComponentActivity() {
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authService = AuthService(this)

        setContent {
            AndTestTheme {
                Navigation(authService)
            }
        }
    }
}