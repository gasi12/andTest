package com.example.andtest.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.service.ServiceInterface

class CustomerSummaryScreenViewModel(
    private val authService: ServiceInterface,
    val sharedViewModel: SharedViewModel
): ViewModel() {
    val isDataPresent: MutableState<Boolean?> = mutableStateOf(false)
    init {
        sharedViewModel.getAllCustomers()
        isDataPresent.value=true
        Log.i("whos calling page?","customer viewmodel init")
    }


}
class CustomerSummaryScreenFactory(private val authService: ServiceInterface, private val sharedViewModel: SharedViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerSummaryScreenViewModel::class.java)) {
            return CustomerSummaryScreenViewModel(authService,sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}