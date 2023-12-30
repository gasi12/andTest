package com.example.andtest.api.dto



data class Device(
    val id: Long,
    val deviceName: String,
    val deviceSerialNumber: String
)

data class StatusHistory(
    val id: Long,
    val status: String,
    val comment: String,
    val time: String
)

data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: Long
)

data class ServiceRequestWithDetailsDto(
    val id: Long,
    val description: String,
    val startDate: String,
    val price: Long,
    val device: Device,
    val lastStatus: String,
    val statusHistory: List<StatusHistory>,
    val customer: Customer
)