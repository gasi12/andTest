package com.example.andtest.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.DeviceWithServiceRequestList
import com.example.andtest.api.service.ServiceInterface


open class DeviceDetailsViewModel(
    private val authDevice: ServiceInterface,
    private val id: String,
    private val sharedViewModel: SharedViewModel
): ViewModel() {


    val device: MutableLiveData<DeviceWithServiceRequestList> = MutableLiveData()
    val isDeleted = mutableStateOf(false)
    val isDataLoaded = mutableStateOf(false)
    val isInit = mutableStateOf(false)
    init
    {
        getDeviceDetails()
    }

     fun getDeviceDetails() {

        authDevice.getDeviceWithServiceRequests(id)  { deviceResponse ->
            isDataLoaded.value=true
            device.value = deviceResponse
            isInit.value=true
        }
    }
    fun deleteThisDevice(){  //todo
//        authDevice.deleteDeviceById(id)
//        {isSuccessful ->
//            if(isSuccessful)
//                isDeleted.value=true
//
//        }
    }
}
class DeviceDetailsFactory(private val authDevice: ServiceInterface, private val id:String, private val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceDetailsViewModel::class.java)) {
            return DeviceDetailsViewModel(authDevice,id,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}