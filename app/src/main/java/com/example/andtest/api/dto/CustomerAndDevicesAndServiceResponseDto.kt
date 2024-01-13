package com.example.andtest.api.dto

data class CustomerAndDevicesAndServiceResponseDto(
    val customer: Customer,
     val device: Device,
     val serviceRequest: ServiceRequestWithId
)
