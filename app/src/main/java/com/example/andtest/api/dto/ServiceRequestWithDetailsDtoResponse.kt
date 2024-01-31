package com.example.andtest.api.dto

import android.util.Log
import java.time.LocalDateTime

data class ServiceRequestEditor(
    val description: String,
    val price: Long
)
data class ServiceRequestWithId(
    val id: Long,
    val description: String,
    val price: Long
)
data class Device(
    val id: Long,
    val deviceName: String,
    val deviceSerialNumber: String,
    val deviceType: DeviceType
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
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val price: Long,
    val device: Device,
    val lastStatus: Status,
    val statusHistoryList: List<StatusHistory>,
    val customer: Customer
)

data class ServiceRequest(
    val id: Long,
    val description: String,
    val lastStatus: Status,
    val endDate: LocalDateTime,
    val startDate: LocalDateTime,
    val price: Long
)

data class DeviceWithServiceRequestList(
    val id: Long,
    val deviceName: String,
    val deviceSerialNumber: String,
    val deviceType: DeviceType,
    val serviceRequestList: List<ServiceRequest>
)
fun String.formatToDateTime():String{

    try {
        return replace("T", " ").trim().dropLast(7)
    } catch (e: Exception) {
        Log.i("formattodatetimeexceptipon", e.toString())
    }
    return ""
}


