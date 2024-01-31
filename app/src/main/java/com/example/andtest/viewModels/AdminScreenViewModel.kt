package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.DeviceWithServiceRequestList
import com.example.andtest.api.service.ServiceInterface

class AdminScreenViewModel(
    val sharedViewModel: SharedViewModel,
     val authService: ServiceInterface
): ViewModel() {
    val isPromoted : MutableState<Boolean?> = mutableStateOf(null)
    val isDataPresent: MutableState<Boolean?> = mutableStateOf(false)
    init {
        sharedViewModel.refreshUsers()
        isDataPresent.value=true
    }
    fun promoteToAdmin(id:Long){
        authService.promoteToAdmin(id){
            isPromoted.value=it
        }
    }
    fun promoteToUser(id:Long){
        authService.promoteToUser(id){
            isPromoted.value=it
        }
    }

}
class AdminScreenViewModelFactory(private val sharedViewModel: SharedViewModel, val authService: ServiceInterface) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminScreenViewModel::class.java)) {
            return AdminScreenViewModel(sharedViewModel,authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}