package com.example.andtest.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.service.ServiceInterface

class ExampleScreenViewModel(private val authService: ServiceInterface):ViewModel() {

    val serviceRequests: MutableLiveData<List<ServiceRequestWithDetailsDto>> = MutableLiveData()
    fun getAllServices() {

        authService.getAllServiceRequestsWithUserName(0, 10)  { serviceRequestsList ->
            serviceRequests.value = serviceRequestsList
        }
    }

}
class ExampleScreenModelFactory(private val authService: ServiceInterface) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExampleScreenViewModel::class.java)) {
            return ExampleScreenViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}