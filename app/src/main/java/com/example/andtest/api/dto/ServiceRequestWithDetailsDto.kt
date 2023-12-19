package com.example.andtest.api.dto


data class StatusHistory(
    val id: Long,
    val status: String,
    val comment: String,
    val time: String
)
data class ServiceRequestWithDetailsDto( //ServiceRequestWithDetailsDto
    val id: Long,
    val description: String,
    val endDate: String,
    val startDate: String,
    val price: Long,
    val customerFirstName: String,
    val customerLastName: String,
    val customerPhoneNumber: Long,
    val userId: Long,
    val lastStatus: String,
    val statusHistory: List<StatusHistory>
)