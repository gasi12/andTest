package com.example.andtest.api.dto

import java.time.LocalDate

data class ServiceRequestWithDetailsDto(
    val id: Long,
    val description: String,
    val status: String,
    val endDate: String,//todo zrobic z tego date
    val startDate: String,
    val price: Double,
    val customerId: Long,
    val customerFirstName: String,
    val customerLastName: String,
    val customerPhoneNumber: String,
    val userId: Long

) {
    override fun toString(): String {
        return "ServiceRequestWithDetailsDto(id=$id, description='$description', status='$status', endDate=$endDate, startDate=$startDate, price=$price, customerId=$customerId, customerFirstName='$customerFirstName', customerLastName='$customerLastName', customerPhoneNumber='$customerPhoneNumber', userId=$userId)"
    }
}

