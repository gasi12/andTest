package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.service.ServiceInterface

class DeviceSummaryScreenViewModel(
    private val authService: ServiceInterface,
    val sharedViewModel: SharedViewModel
): ViewModel() {
    val isDataPresent: MutableState<Boolean?> = mutableStateOf(false)
    init {
        sharedViewModel.getAllDevices()
        isDataPresent.value=true
        Log.i("whos calling page?","device viewmodel init")
    }


}
class DeviceSummaryScreenFactory(private val authService: ServiceInterface, private val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceSummaryScreenViewModel::class.java)) {
            return DeviceSummaryScreenViewModel(authService,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}