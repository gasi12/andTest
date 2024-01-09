package com.example.andtest.viewModels
import android.util.Log
import androidx.compose.foundation.pager.PageSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.service.ServiceInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class ServicesSummaryScreenViewModel(
    private val authService: ServiceInterface,
    val sharedViewModel: SharedViewModel
):ViewModel() {
    val isDataPresent: MutableState<Boolean?> = mutableStateOf(false)
init {
    sharedViewModel.getAllServices()
    isDataPresent.value=true
    Log.i("whos calling page?","viewmodel init")
}


}
class ServicesSummaryScreenFactory(private val authService: ServiceInterface,private val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesSummaryScreenViewModel::class.java)) {
            return ServicesSummaryScreenViewModel(authService,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}