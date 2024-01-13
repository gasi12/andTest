package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.service.ServiceInterface

class CustomerDetailsViewModel(
    private val authService: ServiceInterface,
    private val phoneNumber: Long,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

//    fun shareServiceRequest(price:Long,description:String){
//        val serviceRequest = ServiceRequest(description,price)
//        sharedViewModel.sharedServiceRequest.value= serviceRequest
//        sharedViewModel.sharedServiceRequestId.value =id
//    }
    val customerWithDevices: MutableLiveData<CustomerWithDevicesListDtoResponse?> = MutableLiveData()
    val isDeleted = mutableStateOf(false)
    val isDataPresent = mutableStateOf(false)
    init
    {


        getUserWithDevicesByPhoneNumber(phoneNumber)
    }

    private fun getUserWithDevicesByPhoneNumber(phoneNumber: Long){

        authService.getCustomerWithDevicesByPhoneNumber(phoneNumber){

                response, isSuccess ->
            if(isSuccess&&response!=null){
                //success
                customerWithDevices.value=response
                isDataPresent.value=true
                Log.i("getCustomerWithDevicesByPhoneNumber",response.toString())
            }else if(!isSuccess&&response==null){
                //not found
                isDataPresent.value=false
            }

        }
    }
    fun deleteThisCustomerRequest(){
//        authService.deleteServiceById(id)
//        {isSuccessful ->
//            if(isSuccessful)
//                isDeleted.value=true
//todo implement
        }
    }

class CustomerDetailsFactory(private val authService: ServiceInterface, private val phoneNumber:Long, private val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerDetailsViewModel::class.java)) {
            return CustomerDetailsViewModel(authService,phoneNumber,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}