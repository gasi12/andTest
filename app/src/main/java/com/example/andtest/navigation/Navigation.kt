package com.example.andtest.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.andtest.api.RetrofitClient

import com.example.andtest.screens.LoginScreen
import com.example.andtest.screens.MainScreen
import com.example.andtest.screens.SignupScreen


    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.LOGIN.name){
            composable(route=Screen.HOME.name){

                MainScreen()

            }
            composable(route=Screen.REGISTER.name){
                SignupScreen(navController = navController)
            }
            composable(route=Screen.LOGIN.name){
                LoginScreen(navController = navController)
            }
        }


    }

