package com.example.andtest.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.StatusHistory
import com.example.andtest.api.dto.DeviceType
import com.example.andtest.api.dto.Status
import com.example.andtest.api.service.MockService
import java.time.LocalDateTime
import java.util.Date

class MockViewModel : ServiceDetailsViewModel(sharedViewModel = SharedViewModel(MockService()), authService = MockService(), id = 1L) {
    override val serviceRequest = MutableLiveData(ServiceRequestWithDetailsDto(
        id = 1L,
        description = "Fix the screen",
        startDate = LocalDateTime.now(),
        endDate = LocalDateTime.now().plusDays(4),
        price = 100L,
        device = Device(
            id = 2L,
            deviceName = "Laptop",
            deviceSerialNumber = "ABC123",
            DeviceType.DESKTOP
        ),
        lastStatus = Status.PENDING,
        statusHistoryList = listOf(
            StatusHistory(
                id = 3L,
                status = "Created",
                comment = "Service request created",
                time = "2024-01-04T00:13:47Z"
            )
        ),
        customer = Customer(
            id = 4L,
            firstName = "John",
            lastName = "Doe",
            phoneNumber = 1234567890L
        )
    ))
    override val isDeleted = mutableStateOf(false)
//    override val isDataLoaded = mutableStateOf(false)
    // Override other methods and properties if needed
}