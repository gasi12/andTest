package com.example.andtest.api.dto

data class ServiceRequestWithUserNameDto( //ServiceRequestWithUserNameDto todo rename these guys
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
        return "ServiceRequestWithUserNameDto(id=$id, description='$description', status='$status', endDate=$endDate, startDate=$startDate, price=$price, customerId=$customerId, customerFirstName='$customerFirstName', customerLastName='$customerLastName', customerPhoneNumber='$customerPhoneNumber', userId=$userId)"
    }
}

