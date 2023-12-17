package com.example.andtest.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.service.AuthService
import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.service.ServiceInterface

class LoginScreenViewModel(private val authService: ServiceInterface) : ViewModel() {
    // LiveData to handle login error messages
    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> get() = _loginError

    // MutableState to handle navigation events
    val navigateToHome = mutableStateOf(false)

    // Function to handle login
    fun login(email: String, password: String) {
        val loginBody = LoginBody(email, password)
        authService.successfulresponse(loginBody) { tokens, isSuccess ->
            if (isSuccess) {
                _loginError.value = ""
                navigateToHome.value = true  // Trigger navigation event
            } else {
                _loginError.value = "Invalid username or password!"
            }
        }
    }
}
class LoginViewModelFactory(private val authService: ServiceInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginScreenViewModel::class.java)) {
            return LoginScreenViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}