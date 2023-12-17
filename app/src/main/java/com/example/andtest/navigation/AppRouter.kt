package com.example.andtest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost


enum class Screen {
    HOME,
    REGISTER,
    LOGIN,
    EX,
    EX2
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Register : NavigationItem(Screen.REGISTER.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object Ex : NavigationItem(Screen.EX.name)
    object Ex2 : NavigationItem(Screen.EX2.name)
}