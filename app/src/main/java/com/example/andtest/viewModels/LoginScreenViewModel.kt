package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.service.ServiceInterface

class LoginScreenViewModel(private val authService: ServiceInterface) : ViewModel() {
    // LiveData to handle login error messages



    // MutableState to handle navigation events
    val navigateToHome : MutableState<Boolean?> = mutableStateOf(null)

    // Function to handle login
    fun login(email: String, password: String) {
        navigateToHome.value=null
        val loginRequest = LoginRequest(email, password)
        authService.loginCall(loginRequest) { tokens, isSuccess ->
            if (isSuccess) {
                //_loginError.value = ""
                navigateToHome.value = true  // Trigger navigation event
            } else {
                Log.i("navigatetohome", navigateToHome.value.toString())
                navigateToHome.value=false
              //  _loginError.value = "Invalid username or password!"
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