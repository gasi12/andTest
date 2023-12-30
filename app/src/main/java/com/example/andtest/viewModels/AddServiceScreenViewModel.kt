package com.example.andtest.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import com.example.andtest.api.service.ServiceInterface

class AddServiceScreenViewModel(private val authService: ServiceInterface) : ViewModel() {




}
class AddServiceScreenViewModelFactory(private val authService: ServiceInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddServiceScreenViewModel::class.java)) {
            return AddServiceScreenViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}