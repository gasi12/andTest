package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.dto.RegisterRequest
import com.example.andtest.api.service.ServiceInterface

class SignupScreenViewModel(private val authService: ServiceInterface) : ViewModel() {


    val navigateToHome : MutableState<Boolean?> = mutableStateOf(null)
    val notInvited : MutableState<Boolean?> = mutableStateOf(false)
    fun register(firstName: String,lastName: String, email: String, password: String) {
        Log.i("A","B")
        navigateToHome.value=null
//        notInvited.value=false
        val registerRequest = RegisterRequest(firstName,lastName,email, password)
        authService.register(registerRequest) { tokens, isSuccess ->
            if (isSuccess) {
                if(tokens!=null){
                    navigateToHome.value = true
                }
                else{
                    notInvited.value=true
                }

            }
            else {
                Log.i("navigatetohome", navigateToHome.value.toString())
                navigateToHome.value=false
            }

        }
    }
}
class SignupScreenViewModelFactory(private val authService: ServiceInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupScreenViewModel::class.java)) {
            return SignupScreenViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}