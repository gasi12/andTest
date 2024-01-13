package com.example.andtest.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithId
import com.example.andtest.api.service.ServiceInterface
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

open class ServiceDetailsViewModel(
    private val authService: ServiceInterface,
    private val id: Long,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    fun shareServiceRequest(price:Long,description:String){
        val serviceRequest = ServiceRequest(description,price)
        sharedViewModel.sharedServiceRequest.value= serviceRequest
        sharedViewModel.sharedServiceRequestId.value =id
    }
    open val serviceRequest: MutableLiveData<ServiceRequestWithDetailsDto> = MutableLiveData()
    open val isDeleted = mutableStateOf(false)
    open val isDataLoaded = mutableStateOf(false)
    val isInit = mutableStateOf(false)
    init
    {
        getServiceDetails()

    }

    fun getServiceDetails() {

        authService.getServiceDetails(id)  { serviceRequestsResponse ->
            isDataLoaded.value=true
            serviceRequest.value = serviceRequestsResponse
            isInit.value=true
        }
    }
    fun deleteThisServiceRequest(){
        authService.deleteServiceById(id)
        {isSuccessful ->
            if(isSuccessful)
                isDeleted.value=true

        }
    }
}
class ServiceDetailsFactory(private val authService: ServiceInterface,private val id:Long, private val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceDetailsViewModel::class.java)) {
            return ServiceDetailsViewModel(authService,id,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}