package com.example.andtest.navigation


enum class Screen(val visibleName: String) {
    HOME("Home"),
    REGISTER("Register"),
    LOGIN("Login"),
    SERVICESUMMARY("Service Summary"),
    SERVICEDETAILS("Service Details"),
    ADDSERVICE("Add Service"),
    ADDSTATUS("Add Status"),
    EDITSERVICE("Edit Service"),
    CUSTOMERSUMMARY("Customer Summary"),
    QRScanner("QR Scanner"),
    CUSTOMERDETAILS("Customer Details"),
    DEVICESUMMARY("Device Summary"),
    DEVICEDETAILS("Device Details"),
    ADMINSCREEN("Admin Screen"),
    INVITEUSER("Invite User")
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Register : NavigationItem(Screen.REGISTER.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object Summary : NavigationItem(Screen.SERVICESUMMARY.name)
    object Details : NavigationItem(Screen.SERVICEDETAILS.name)

    object AddService : NavigationItem(Screen.ADDSERVICE.name)
    object AddStatus : NavigationItem(Screen.ADDSTATUS.name)
    object EditService : NavigationItem(Screen.EDITSERVICE.name)
    object Customer : NavigationItem(Screen.CUSTOMERSUMMARY.name)
    object QRScanner : NavigationItem(Screen.QRScanner.name)

    object CustomerDetails : NavigationItem(Screen.CUSTOMERDETAILS.name)

    object DeviceSummary : NavigationItem(Screen.DEVICESUMMARY.name)

    object DeviceDetails : NavigationItem(Screen.DEVICEDETAILS.name)
}