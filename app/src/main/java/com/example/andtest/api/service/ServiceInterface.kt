package com.example.andtest.api.service

import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceRequestsDto
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceResponseDto
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.DeviceWithServiceRequestList
import com.example.andtest.api.dto.InviteRequest
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.RegisterRequest
import com.example.andtest.api.dto.ServiceRequestEditor
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import com.example.andtest.api.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body

interface ServiceInterface {
    fun loginCall(body: LoginRequest, callback: (LoginResponse?, Boolean) -> Unit)

    fun promoteToUser(id:Long,callback:(Boolean)-> Unit)
    fun promoteToAdmin(id:Long,callback:(Boolean)-> Unit)
    fun register(body: RegisterRequest, callback: (LoginResponse?, Boolean) -> Unit)
    fun refreshToken(body: RefreshTokenRequest, callback: (LoginResponse?, Boolean) -> Unit)

    fun getAllServiceRequestsWithUserName(page: Int?,pageSize: Int?, callback: (List<ServiceRequestWithUserNameDtoResponse>) -> Unit)

    fun getServiceDetails(id:Long, callback: (ServiceRequestWithDetailsDto) -> Unit)

    fun deleteServiceById(id:Long,callback:(Boolean)-> Unit)

    fun deleteUserById(id:Long,callback:(Boolean)-> Unit)
    fun inviteUser(body: InviteRequest,callback:(Boolean)-> Unit)

    fun addStatusToService(serviceId : Long, body: StatusHistoryDtoRequest,callback: (StatusHistoryDtoRequest?,Boolean) -> Unit)


    fun getCustomerWithDevicesByPhoneNumber(phoneNumber: Long, callback: (CustomerWithDevicesListDtoResponse?, Boolean) -> Unit)

    fun addCustomerWithDeviceAndService(body: CustomerAndDevicesAndServiceRequestsDto, callback: (CustomerAndDevicesAndServiceResponseDto?, Boolean)->Unit)

    fun editService(id : Long, body: ServiceRequestEditor, callback: (Boolean) -> Unit)
    fun getCustomerList(page: Int?,pageSize: Int?, callback: (List<Customer>)-> Unit)

    fun getDeviceList(page: Int?,pageSize: Int?, callback: (List<Device>)-> Unit)

    fun getUserList(page: Int?,pageSize: Int?, callback: (List<UserDto>)-> Unit)

    fun getDeviceWithServiceRequests(serialNumber: String, callback: (DeviceWithServiceRequestList) -> Unit)
}