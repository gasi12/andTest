package com.example.andtest.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.andtest.SecurePreferences
import com.example.andtest.api.RetrofitClient
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.service.AuthService
import com.example.andtest.screens.ExampleScreen
import com.example.andtest.screens.ExampleScreen2

import com.example.andtest.screens.LoginScreen
import com.example.andtest.screens.MainScreen
import com.example.andtest.screens.SignupScreen
import com.example.andtest.viewModels.ExampleScreenModelFactory
import com.example.andtest.viewModels.ExampleScreenViewModel
import com.example.andtest.viewModels.LoginScreenViewModel
import com.example.andtest.viewModels.LoginViewModelFactory


@Composable
    fun Navigation(authService:AuthService) {
        val navController = rememberNavController()
    val securePreferences = SecurePreferences
        .getInstance(LocalContext.current)
    val refreshToken = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)?:""
    var startDestination by remember { mutableStateOf<String?>(null) }
Log.i("refreshtokendowyslania",refreshToken)
    LaunchedEffect(Unit) {
        authService.refreshToken(RefreshTokenBody(refreshToken)) { tokens, isSuccess ->


           if (isSuccess && tokens?.token!=null) {
               Log.i("TAG W LOGIN SCREENIE", tokens.refreshToken)
               startDestination =   Screen.HOME.name

            } else {
               startDestination =   Screen.LOGIN.name
            }
        }
    }
    startDestination?.let { destination ->
        NavHost(navController = navController, startDestination = startDestination!!) {
            composable(route = Screen.HOME.name) {
                Log.i("navigation route","$startDestination" )
                MainScreen(navController = navController,authService)

            }
            composable(route = Screen.REGISTER.name) {
                Log.i("navigation route","$startDestination" )
                SignupScreen(navController = navController)
            }
            composable(route = Screen.LOGIN.name) {
                Log.i("navigation route","$startDestination" )
                val viewModel: LoginScreenViewModel =
                    viewModel(factory = LoginViewModelFactory(authService))
                LoginScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = Screen.EX.name) {
                Log.i("navigation route","$startDestination" )
                val viewModel:ExampleScreenViewModel =
                    viewModel(factory = ExampleScreenModelFactory(authService))
                ExampleScreen(viewModel,navController)
            }
            composable(route = Screen.EX2.name) {
                Log.i("navigation route","$startDestination" )

                ExampleScreen2(navController)
            }

        }

    }
    }

