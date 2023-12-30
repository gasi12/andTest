package com.example.andtest.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.service.ServiceInterface

class ServiceDetailsViewModel(private val authService: ServiceInterface,private val id: Long): ViewModel() {

    val serviceRequest: MutableLiveData<ServiceRequestWithDetailsDto> = MutableLiveData()
    init {
        getServiceDetails(id)
    }
    fun getServiceDetails(id:Long) {

        authService.getServiceDetails(id)  { serviceRequestsResponse ->
            serviceRequest.value = serviceRequestsResponse
        }
    }

}
class ServiceDetailsFactory(private val authService: ServiceInterface,private val id:Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceDetailsViewModel::class.java)) {
            return ServiceDetailsViewModel(authService,id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}