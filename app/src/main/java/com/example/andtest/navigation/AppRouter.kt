package com.example.andtest.navigation


enum class Screen {
    HOME,
    REGISTER,
    LOGIN,
    SUMMARY,
    DETAILS,
    ADDSERVICE
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Register : NavigationItem(Screen.REGISTER.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object Summary : NavigationItem(Screen.SUMMARY.name)
    object Details : NavigationItem(Screen.DETAILS.name)

    object AddService : NavigationItem(Screen.ADDSERVICE.name)
}