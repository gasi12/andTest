package com.example.andtest.api

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
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {


    @POST("/auth/authenticate/user")
    fun login(@Body body: LoginRequest): Call<LoginResponse>


    @POST("/auth/refresh")
    fun refreshToken(@Body body: RefreshTokenRequest): Call<LoginResponse>


    @GET("/services/services") //services summary
    fun getAllServiceRequestsWithUserName(
        @Query("page") page: Int? = 0,
        @Query("pageSize")pageSize: Int? = 10
    ): Call<List<ServiceRequestWithUserNameDtoResponse>>

    @GET("/services/service/{id}")
    fun getServiceDetails(
        @Path("id") id : Long
    ): Call<ServiceRequestWithDetailsDto>

    @DELETE("/services/service/{id}")
    fun deleteServiceById(
        @Path("id") id : Long
    ): Call<Void>
@POST("/services/service/{serviceId}/status")
    fun addStatusToService(
        @Path("serviceId") serviceId : Long,
        @Body body: StatusHistoryDtoRequest
    ): Call<StatusHistoryDtoRequest>


    @GET("/customer/phoneNumber/{phoneNumber}")
    fun getUserWithDevicesByPhoneNumber(
        @Path("phoneNumber") phoneNumber: Long
    ): Call<CustomerWithDevicesListDtoResponse>


    @POST("/customer")
    fun addCustomerWithDeviceAndService(
        @Body body: CustomerAndDevicesAndServiceRequestsDto
    ): Call<CustomerAndDevicesAndServiceResponseDto>

    @PUT("/services/service/{id}")
    fun editService(
        @Path("id") id : Long,
        @Body body: ServiceRequestEditor
    ): Call<ServiceRequestEditor>

    @GET("/customer/getall/pageable") //services summary
    fun getAllCustomers(
        @Query("page") page: Int? = 0,
        @Query("pageSize")pageSize: Int? = 10
    ): Call<List<Customer>>

    @GET("/device/all/pageable") //services summary
    fun getAllDevices(
        @Query("page") page: Int? = 0,
        @Query("pageSize")pageSize: Int? = 10
    ): Call<List<Device>>

    @GET("/device/serialNumber/{serialNumber}")
    fun getDeviceWithServiceRequestsBySerialNumber(
        @Path("serialNumber") serialNumber: String
    ): Call<DeviceWithServiceRequestList>

    @GET("/admin/users") //services summary
    fun getUserList(
        @Query("page") page: Int? = 0,
        @Query("pageSize")pageSize: Int? = 10
    ): Call<List<UserDto>>

    @POST("/admin/invite")
    fun inviteUser(@Body body: InviteRequest): Call<Void>

    @DELETE("/admin/user/{id}")
    fun deleteUserById(
        @Path("id") id : Long
    ): Call<Void>

    @POST("/auth/register/user")
    fun register(@Body body: RegisterRequest): Call<LoginResponse>

    @POST("/admin/user/{id}/admin")
    fun promoteToAdmin(
        @Path("id") id : Long
    ): Call<Void>

    @POST("/admin/user/{id}/user")
    fun promoteToUser(
        @Path("id") id : Long
    ): Call<Void>
}


