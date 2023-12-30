package com.example.andtest.navigation

import android.util.Log
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
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.service.AuthService
import com.example.andtest.screens.AddServiceScreen
import com.example.andtest.screens.AddStatusScreen
import com.example.andtest.screens.ServicesSummaryScreen
import com.example.andtest.screens.ServiceDetailsScreen

import com.example.andtest.screens.LoginScreen
import com.example.andtest.screens.MainScreen
import com.example.andtest.screens.SignupScreen
import com.example.andtest.viewModels.AddServiceScreenViewModel
import com.example.andtest.viewModels.AddServiceScreenViewModelFactory
import com.example.andtest.viewModels.AddStatusScreenViewModel
import com.example.andtest.viewModels.AddStatusScreenViewModelFactory
import com.example.andtest.viewModels.ServicesSummaryScreenFactory
import com.example.andtest.viewModels.ServicesSummaryScreenViewModel
import com.example.andtest.viewModels.LoginScreenViewModel
import com.example.andtest.viewModels.LoginViewModelFactory
import com.example.andtest.viewModels.ServiceDetailsFactory
import com.example.andtest.viewModels.ServiceDetailsViewModel


@Composable
    fun Navigation(authService:AuthService) {
        val navController = rememberNavController()
    val securePreferences = SecurePreferences
        .getInstance(LocalContext.current)
    val refreshToken = securePreferences.getToken(SecurePreferences.TokenType.REFRESH)?:""
    var startDestination by remember { mutableStateOf<String?>(null) }
Log.i("refreshtokendowyslania",refreshToken)
    LaunchedEffect(Unit) {
        authService.refreshToken(RefreshTokenRequest(refreshToken)) { tokens, isSuccess ->


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
            composable(route = Screen.SUMMARY.name) {
                Log.i("navigation route","$startDestination" )
                val viewModel:ServicesSummaryScreenViewModel =
                    viewModel(factory = ServicesSummaryScreenFactory(authService))
                ServicesSummaryScreen(viewModel,navController)
            }
            composable(route = "${Screen.DETAILS.name}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toLong()
                if(id != null){
                    val viewModel: ServiceDetailsViewModel =
                        viewModel(factory = ServiceDetailsFactory(authService, id))
                    ServiceDetailsScreen(viewModel, navController)
                }
            }
            composable(route = Screen.ADDSERVICE.name) {
                Log.i("navigation route","$startDestination" )
                val viewModel:AddServiceScreenViewModel =
                    viewModel(factory = AddServiceScreenViewModelFactory(authService))
                AddServiceScreen(navController = navController,viewModel=  viewModel)
            }
            composable(route = "${Screen.ADDSTATUS.name}/{serviceId}") { backStackEntry ->
                val serviceId = backStackEntry.arguments?.getString("serviceId")?.toLong()
                if(serviceId != null){
                    val viewModel: AddStatusScreenViewModel =
                        viewModel(factory = AddStatusScreenViewModelFactory(authService, serviceId))
                    AddStatusScreen( navController,viewModel)
                }
            }
        }

    }
    }

