package com.example.andtest.api.service

import com.example.andtest.api.dto.LoginBody
import com.example.andtest.api.dto.RefreshTokenBody
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDto
import com.example.andtest.api.dto.StatusHistory

class MockService :ServiceInterface{
    override fun loginCall(body: LoginBody, callback: (LoginResponse?, Boolean) -> Unit) {
        // Simulate a successful response
        val loginResponse = LoginResponse("mockAuthToken", "mockRefreshToken","mockusername","mockfirstname","mocklastname")
        callback(loginResponse, true)
    }

 override fun refreshToken(body: RefreshTokenBody, callback: (LoginResponse?, Boolean) -> Unit) {
        // Simulate a successful token refresh
     val loginResponse = LoginResponse("mockAuthToken", "mockRefreshToken","mockusername","mockfirstname","mocklastname")
        callback(loginResponse, true)

    }



    override fun getAllServiceRequestsWithUserName(
        pageNo: Int?,
        pageSize: Int?,
        callback: (List<ServiceRequestWithUserNameDto>) -> Unit
    ) {
        val serviceRequests = mutableListOf<ServiceRequestWithUserNameDto>()
        val avilableStatus = listOf("PENDING","ON_HOLD","FINISHED","IN_PROCESS")
        for (i in 1..10) {
            val serviceRequest = ServiceRequestWithUserNameDto(
                id = i.toLong(),
                status = avilableStatus.random(),
                description = "Service description $i",
                endDate = "2023-12-05",
                startDate = "2023-11-14",
                price = (80.00*i)/3,
                customerFirstName = "Json $i",
                customerLastName = "Wick",
                userId = i.toLong(),
                customerId = i.toLong(),
                customerPhoneNumber = "1234535"

            )
            serviceRequests.add(serviceRequest)
        }
        callback(serviceRequests)
    }

    override fun getServiceDetails(id: Long, callback: (ServiceRequestWithDetailsDto) -> Unit) {
        val service= ServiceRequestWithDetailsDto(
            id = 1L,
            customerPhoneNumber = 123456789L,
            userId = 2L,
            customerLastName = "Wick",
            customerFirstName = "Json",
            price = 12345,
            startDate = "2023-12-22",
            endDate = "2024-11-01",
            description = "that some long desciption to fill as many space as possible. This normally doesnt get any much longer than this," +
                    " but keep in mind to make a bit more space for special occasion",
            lastStatus = "ON_HOLD",
            statusHistory = listOf(
                StatusHistory(1L,"PENDING","Request created","2023-11-11"),
                StatusHistory(42L,"ON_HOLD","Keep in mind these id's ain't incrementing by 1","2023-11-12"),
                StatusHistory(42L,"Finished","blah blah blah","2023-11-13"),
            )
        )
        callback(service)
    }

    override fun deleteServiceById(id: Long, callback: (Boolean) -> Unit) {
        callback(true)
    }
}
