package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.service.ServiceInterface

class EditServiceScreenViewModel(private val authService: ServiceInterface,
                                 private val id: Long,
                                  val sharedViewModel: SharedViewModel
): ViewModel() {
    val isSentSuccessfully: MutableState<Boolean?> = mutableStateOf(null)


fun editService(body: ServiceRequest){
    Log.i("edit id",id.toString())
    isSentSuccessfully.value=null
authService.editService(id,body){
    isSuccess->
    isSentSuccessfully.value = isSuccess
}

}


}
class EditServiceScreenViewModelFactory(private val authService: ServiceInterface, private val id:Long,  val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditServiceScreenViewModel::class.java)) {
            return EditServiceScreenViewModel(authService,id,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}