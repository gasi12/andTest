package com.example.andtest.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.andtest.api.RetrofitClient
import com.example.andtest.api.service.AuthService
import com.example.andtest.screens.ExampleScreen
import com.example.andtest.screens.ExampleScreen2

import com.example.andtest.screens.LoginScreen
import com.example.andtest.screens.MainScreen
import com.example.andtest.screens.SignupScreen
import com.example.andtest.viewModels.LoginScreenViewModel
import com.example.andtest.viewModels.LoginViewModelFactory


@Composable
    fun Navigation(authService:AuthService) {
        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = Screen.HOME.name){
            composable(route=Screen.HOME.name){

                MainScreen(navController = navController)

            }
            composable(route=Screen.REGISTER.name){
                SignupScreen(navController = navController)
            }
            composable(route=Screen.LOGIN.name){
                val viewModel: LoginScreenViewModel = viewModel(factory = LoginViewModelFactory(authService))
                LoginScreen(navController = navController, viewModel = viewModel)
            }
            composable(route=Screen.EX.name){

               ExampleScreen()
            }
            composable(route=Screen.EX2.name){

               ExampleScreen2()
            }

        }


    }

