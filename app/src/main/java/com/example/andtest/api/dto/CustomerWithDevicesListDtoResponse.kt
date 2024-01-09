package com.example.andtest.api.dto
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.Device

data class CustomerWithDevicesListDtoResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: Long,
    val devices: List<Device>
)

