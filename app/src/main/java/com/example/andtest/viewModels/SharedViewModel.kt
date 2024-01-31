package com.example.andtest.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.ServiceRequestEditor
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.UserDto
import com.example.andtest.api.service.ServiceInterface
import com.example.andtest.navigation.Screen

class SharedViewModel(private val authService: ServiceInterface) : ViewModel() {
    val topBarQuery = mutableStateOf("")
    val selectedItem = mutableStateOf(Screen.SERVICESUMMARY.name)

    val sharedServiceRequestId: MutableLiveData<Long> = MutableLiveData()
    val sharedServiceRequestEditor: MutableLiveData<ServiceRequestEditor> = MutableLiveData()

    val serviceRequests: MutableLiveData<List<ServiceRequestWithUserNameDtoResponse>> = MutableLiveData(emptyList())
    val isLastPageEmptyServices : MutableState<Boolean?> = mutableStateOf(false)
    private var currentPageServices = 0

    val customers: MutableLiveData<List<Customer>> = MutableLiveData(emptyList())
    val isLastPageEmptyCustomers : MutableState<Boolean?> = mutableStateOf(false)
    private var currentPageCustomer = 0

    val devices: MutableLiveData<List<Device>> = MutableLiveData(emptyList())
    val isLastPageEmptyDevices : MutableState<Boolean?> = mutableStateOf(false)
    private var currentPageDevice = 0

    val users: MutableLiveData<List<UserDto>> = MutableLiveData(emptyList())
    val isLastPageEmptyUsers : MutableState<Boolean?> = mutableStateOf(false)
    private var currentPageUsers = 0

    fun getAllServices(pageSize: Int = 40) {
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
    fun getAllCustomers(pageSize: Int = 40) {
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
    fun getAllDevices(pageSize: Int = 40) {
        if(isLastPageEmptyDevices.value == true)return
        else{

            authService.getDeviceList(currentPageDevice, pageSize) { deviceList ->
                if(deviceList.isEmpty()){
                    isLastPageEmptyDevices.value=true
                }
                else{
                    devices.value =   devices.value?.plus(deviceList)
                    currentPageDevice++
                }

            }
        }
    }
    fun getAllUsers(pageSize: Int = 40) {
        if(isLastPageEmptyUsers.value == true)return
        else{

            authService.getUserList(currentPageUsers, pageSize) { userList ->
                if(userList.isEmpty()){
                    isLastPageEmptyUsers.value=true
                }
                else{
                    users.value =   users.value?.plus(userList)
                    currentPageUsers++
                }

            }
        }
    }
    fun deleteServiceRequestById(serviceId: Long){
        authService.deleteServiceById(serviceId)
        {isSuccessful ->
            if(isSuccessful)
                serviceRequests.value = serviceRequests.value?.filterNot { it.id == serviceId }
        }
    }
    fun deleteUserById(id: Long){
        authService.deleteUserById(id)
        {isSuccessful ->
            if(isSuccessful)
                users.value = users.value?.filterNot { it.id == id }
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
    fun refreshDevices() {
        currentPageDevice = 0
        isLastPageEmptyDevices.value=false
        devices.value = emptyList()
        getAllDevices()
    }
    fun refreshUsers() {
        currentPageUsers = 0
        isLastPageEmptyUsers.value=false
        users.value = emptyList()
        getAllUsers()
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
