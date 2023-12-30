package com.example.andtest.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.service.ServiceInterface

class ServicesSummaryScreenViewModel(private val authService: ServiceInterface):ViewModel() {

    val serviceRequests: MutableLiveData<List<ServiceRequestWithUserNameDtoResponse>> = MutableLiveData()
    fun getAllServices(callback: () -> Unit) {
        authService.getAllServiceRequestsWithUserName(0, 10) { serviceRequestsList ->
            serviceRequests.value = serviceRequestsList
            callback()
        }
    }
    fun deleteById(serviceId: Long){
        authService.deleteServiceById(serviceId)
        {isSuccessful ->
            if(isSuccessful)
                getAllServices {  }

        }
    }
}
class ServicesSummaryScreenFactory(private val authService: ServiceInterface) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesSummaryScreenViewModel::class.java)) {
            return ServicesSummaryScreenViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}