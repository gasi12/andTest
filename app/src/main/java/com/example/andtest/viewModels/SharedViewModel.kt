package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.service.ServiceInterface
import com.example.andtest.navigation.Screen

class SharedViewModel(private val authService: ServiceInterface) : ViewModel() {
    val topBarQuery = mutableStateOf("")
    val selectedItem = mutableStateOf(Screen.SUMMARY.name)

    val sharedServiceRequestId: MutableLiveData<Long> = MutableLiveData()
    val sharedServiceRequest: MutableLiveData<ServiceRequest> = MutableLiveData()

    val serviceRequests: MutableLiveData<List<ServiceRequestWithUserNameDtoResponse>> = MutableLiveData(emptyList())
    val isLastPageEmptyServices : MutableState<Boolean?> = mutableStateOf(false)
    private var currentPageServices = 0

    val customers: MutableLiveData<List<Customer>> = MutableLiveData(emptyList())
    val isLastPageEmptyCustomers : MutableState<Boolean?> = mutableStateOf(false)
    private var currentPageCustomer = 0



    fun getAllServices(pageSize: Int = 10) {
        if(isLastPageEmptyServices.value == true)return
        else{
            authService.getAllServiceRequestsWithUserName(currentPageServices, pageSize) { serviceRequestsList ->
                if(serviceRequestsList.isEmpty()){
                    isLastPageEmptyServices.value=true
                }
                else{
                    serviceRequests.value =   serviceRequests.value?.plus(serviceRequestsList)
                    currentPageServices++
                }

            }
        }
    }
    fun getAllCustomers(pageSize: Int = 10) {
        if(isLastPageEmptyCustomers.value == true)return
        else{

            authService.getCustomerList(currentPageCustomer, pageSize) { customerList ->
                if(customerList.isEmpty()){
                    isLastPageEmptyCustomers.value=true
                }
                else{
                    customers.value =   customers.value?.plus(customerList)
                    currentPageCustomer++
                }

            }
        }
    }
    fun deleteById(serviceId: Long){
        authService.deleteServiceById(serviceId)
        {isSuccessful ->
            if(isSuccessful)
                serviceRequests.value = serviceRequests.value?.filterNot { it.id == serviceId }
        }
    }

    fun refreshServices() {
        currentPageServices = 0
        isLastPageEmptyServices.value=false
        serviceRequests.value = emptyList()
        getAllServices()
    }

    fun refreshCustomers() {
        currentPageCustomer = 0
        isLastPageEmptyCustomers.value=false
        customers.value = emptyList()
        getAllCustomers()
    }
}
class SharedViewModelFactory(private val authService: ServiceInterface) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
