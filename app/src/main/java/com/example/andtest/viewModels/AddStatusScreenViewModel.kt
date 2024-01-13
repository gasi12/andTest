package com.example.andtest.viewModels

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.SecurePreferences
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import com.example.andtest.api.service.ServiceInterface

class AddStatusScreenViewModel(private val authService: ServiceInterface,  val serviceId: Long): ViewModel() {
    val status : MutableLiveData<StatusHistoryDtoRequest> = MutableLiveData()
    val isBodyPresent = MutableLiveData<Boolean>()

    fun  addStatusToService(id:Long=serviceId,body:StatusHistoryDtoRequest) {

        authService.addStatusToService(id,body) { statusResponse, isSuccess ->
            if(isSuccess) {
                isBodyPresent.value=true
                status.value = statusResponse!!
            }
        }
    }


}
class AddStatusScreenViewModelFactory(private val authService: ServiceInterface, val serviceId: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStatusScreenViewModel::class.java)) {
            return AddStatusScreenViewModel(authService,serviceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}