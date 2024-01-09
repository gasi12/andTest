package com.example.andtest.api.service

import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceRequestsDto
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.StatusHistory
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import com.example.andtest.api.dto.DeviceType
import com.example.andtest.api.dto.Status
import java.time.LocalDateTime
import java.util.Date

class MockService :ServiceInterface{
    override fun loginCall(body: LoginRequest, callback: (LoginResponse?, Boolean) -> Unit) {
        // Simulate a successful response
        val loginResponse = LoginResponse("mockAuthToken", "mockRefreshToken","mockusername","mockfirstname","mocklastname")
        callback(loginResponse, true)
    }

 override fun refreshToken(body: RefreshTokenRequest, callback: (LoginResponse?, Boolean) -> Unit) {
        // Simulate a successful token refresh
            val loginResponse = LoginResponse("mockAuthToken", "mockRefreshToken","mockusername","mockfirstname","mocklastname")
        callback(loginResponse, true)

    }

    override fun addStatusToService(
        serviceId: Long,
        body: StatusHistoryDtoRequest,
        callback: (StatusHistoryDtoRequest?, Boolean) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllServiceRequestsWithUserName(
        pageNo: Int?,
        pageSize: Int?,
        callback: (List<ServiceRequestWithUserNameDtoResponse>) -> Unit
    ) {
        val serviceRequests = mutableListOf<ServiceRequestWithUserNameDtoResponse>()
        val avilableStatus = listOf("PENDING","ON_HOLD","FINISHED","IN_PROCESS")
        for (i in 1..10) {
            val serviceRequest = ServiceRequestWithUserNameDtoResponse(
                id = i.toLong(),
                lastStatus = avilableStatus.random(),
                description = "Service description $i",
                endDate = LocalDateTime.now().plusDays(5),
                startDate = LocalDateTime.now(),
                price = (80L*i)/3,
                customerFirstName = "Json $i",
                customerLastName = "Wick",
                userId = i.toLong(),
                customerId = i.toLong(),
                customerPhoneNumber = "1234535",
                deviceName = "Dell",
                deviceType = DeviceType.DESKTOP

            )
            serviceRequests.add(serviceRequest)
        }
        callback(serviceRequests)
    }

    override fun getServiceDetails(id: Long, callback: (ServiceRequestWithDetailsDto) -> Unit) {
        val service= ServiceRequestWithDetailsDto(
            id = 1L,
            customer = Customer(1, firstName = "john","wick",123456677),


            price = 12345,
            startDate = LocalDateTime.now(),

            endDate = LocalDateTime.now().plusDays(4),
            device = Device(5,"Car","1232456",DeviceType.DESKTOP),
            description = "that some long desciption to fill as many space as possible. This normally doesnt get any much longer than this," +
                    " but keep in mind to make a bit more space for special occasion",
            lastStatus = Status.ON_HOLD,
            statusHistoryList = listOf(
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

    override fun getCustomerWithDevicesByPhoneNumber(
        phoneNumber: Long,
        callback: (CustomerWithDevicesListDtoResponse?, Boolean) -> Unit
    ) {

    }

    override fun addCustomerWithDeviceAndService(
        body: CustomerAndDevicesAndServiceRequestsDto,
        callback: (CustomerAndDevicesAndServiceRequestsDto?, Boolean) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun editService(id: Long, body: ServiceRequest, callback: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCustomerList(page: Int?, pageSize: Int?, callback: (List<Customer>) -> Unit) {
        TODO("Not yet implemented")
    }
}
