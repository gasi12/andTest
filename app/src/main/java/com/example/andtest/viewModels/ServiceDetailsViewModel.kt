package com.example.andtest.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.ServiceRequestEditor
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.service.ServiceInterface

open class ServiceDetailsViewModel(
    private val authService: ServiceInterface,
    private val id: Long,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    fun shareServiceRequest(price:Long,description:String){
        val serviceRequestEditor = ServiceRequestEditor(description,price)
        sharedViewModel.sharedServiceRequestEditor.value= serviceRequestEditor
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