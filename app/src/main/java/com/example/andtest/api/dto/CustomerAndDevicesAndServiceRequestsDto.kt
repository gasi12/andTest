package com.example.andtest.api.dto

data class CustomerAndDevicesAndServiceRequestsDto(
        private val customer: Customer,
    private val device: Device,
    private val serviceRequest: ServiceRequest
)



