package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.InviteRequest
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.service.ServiceInterface

class InviteUserViewModel(private val authService: ServiceInterface) : ViewModel() {


    val isSentSuccessfully: MutableState<Boolean?> = mutableStateOf(null)

    fun inviteUser(email: String){
        authService.inviteUser(InviteRequest(email)){
            isSentSuccessfully.value = it
    }

    }
    fun resetState(){
        isSentSuccessfully.value = null
    }



    }
class InviteUserViewModelFactory(private val authService: ServiceInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InviteUserViewModel::class.java)) {
            return InviteUserViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}