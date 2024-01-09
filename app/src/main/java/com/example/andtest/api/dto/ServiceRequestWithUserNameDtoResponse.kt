package com.example.andtest.api.dto

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ServiceRequestWithUserNameDtoResponse(
    val id: Long,
    val description: String,
    val lastStatus: String,
    val endDate: LocalDateTime,//todo zrobic z tego date
    val startDate: LocalDateTime,
    val price: Long,
    val customerId: Long,
    val deviceName: String,
    val deviceType: DeviceType,
    val customerFirstName: String,
    val customerLastName: String,
    val customerPhoneNumber: String,
    val userId: Long
)

{



}
