package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceRequestsDto
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.service.ServiceInterface

class AddServiceScreenViewModel(
    private val authService: ServiceInterface,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {
    val customerWithDevices : MutableLiveData<CustomerWithDevicesListDtoResponse?> = MutableLiveData()
    val isSentSuccessfully: MutableState<Boolean?> = mutableStateOf(null)
    val isDataPresent: MutableState<Boolean?> = mutableStateOf(null)
    val createdServiceId: MutableState<Long?> = mutableStateOf(null)

fun getUserWithDevicesByPhoneNumber(phoneNumber: Long){

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
    fun addCustomerWithDeviceAndService(body: CustomerAndDevicesAndServiceRequestsDto){
        Log.i("Zjebalem ostro", body.toString())
        authService.addCustomerWithDeviceAndService(body){
            data,isSuccess->
              createdServiceId.value=data?.serviceRequest?.id
            isSentSuccessfully.value = isSuccess
            sharedViewModel.refreshServices()
            sharedViewModel.refreshDevices()
            sharedViewModel.refreshCustomers()
        }
    }
fun clearIsDataPresent(){
    isDataPresent.value=null
}

}
class AddServiceScreenViewModelFactory(private val authService: ServiceInterface,  private val sharedViewModel: SharedViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddServiceScreenViewModel::class.java)) {
            return AddServiceScreenViewModel(authService,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}