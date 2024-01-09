package com.example.andtest.api.service

import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceRequestsDto
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.StatusHistoryDtoRequest

interface ServiceInterface {
    fun loginCall(body: LoginRequest, callback: (LoginResponse?, Boolean) -> Unit)
    fun refreshToken(body: RefreshTokenRequest, callback: (LoginResponse?, Boolean) -> Unit)

    fun getAllServiceRequestsWithUserName(page: Int?,pageSize: Int?, callback: (List<ServiceRequestWithUserNameDtoResponse>) -> Unit)

    fun getServiceDetails(id:Long, callback: (ServiceRequestWithDetailsDto) -> Unit)

    fun deleteServiceById(id:Long,callback:(Boolean)-> Unit)

    fun addStatusToService(serviceId : Long, body: StatusHistoryDtoRequest,callback: (StatusHistoryDtoRequest?,Boolean) -> Unit)


    fun getCustomerWithDevicesByPhoneNumber(phoneNumber: Long, callback: (CustomerWithDevicesListDtoResponse?, Boolean) -> Unit)

    fun addCustomerWithDeviceAndService(body: CustomerAndDevicesAndServiceRequestsDto, callback: (CustomerAndDevicesAndServiceRequestsDto?,Boolean)->Unit)

    fun editService(id : Long, body: ServiceRequest, callback: (Boolean) -> Unit)
    fun getCustomerList(page: Int?,pageSize: Int?, callback: (List<Customer>)-> Unit)

}