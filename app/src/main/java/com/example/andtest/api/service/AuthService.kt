package com.example.andtest.api.service

import android.content.Context
import android.util.Log
import com.example.andtest.SecurePreferences
import com.example.andtest.api.MyApi
import com.example.andtest.api.RetrofitClient
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceRequestsDto
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceResponseDto
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.LoginRequest
import com.example.andtest.api.dto.RefreshTokenRequest
import com.example.andtest.api.dto.LoginResponse
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService private constructor(context: Context):ServiceInterface {
    private val authApi: MyApi
    private val myStorage: SecurePreferences

    init {
        val retrofitClient = RetrofitClient.getClient(context)
        authApi = retrofitClient.create(MyApi::class.java)
        myStorage = SecurePreferences.getInstance(context)
    }
    companion object {
        @Volatile private var instance: AuthService? = null

        fun getInstance(context: Context): AuthService {
            return instance ?: synchronized(this) {
                instance ?: AuthService(context.applicationContext).also { instance = it }
            }
        }
    }
    override fun loginCall(
        body: LoginRequest,
        callback: (loginResponse: LoginResponse?, success: Boolean) -> Unit
    ) {
        // Implement the logic to make the API call and handle the response
        authApi.login(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val tokens = response.body()
                    if (tokens != null) {
                        Log.i("login tokens", response.body().toString())
                        myStorage.saveToken(tokens.token, SecurePreferences.TokenType.AUTH)
                        myStorage.saveToken(tokens.refreshToken, SecurePreferences.TokenType.REFRESH)
                        myStorage.saveAnything("username", tokens.username)
                        myStorage.saveAnything("firstName", tokens.firstName)
                        myStorage.saveAnything("lastName", tokens.lastName)
                    }
                    callback(tokens, true)
                } else {

                    Log.i("login tokens fail", response.body().toString())
                    Log.i(
                        "login tokens fail",
                        "${myStorage.getToken(SecurePreferences.TokenType.AUTH)}"
                    )
                    myStorage.clearTokens()
                    callback(null, false)

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                myStorage.clearTokens()
                Log.i("login request failed", "${t.message}")
                callback(null, false)
            }
        })
    }

    override fun refreshToken(
        body: RefreshTokenRequest,
        callback: (LoginResponse?, Boolean) -> Unit
    ) {

        authApi.refreshToken(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.i("refreshtoken","response ${response.body()?.token}")
                if (response.isSuccessful&&response.body()?.token!=null) {
                    val tokens = response.body()
                    Log.i("tokeny authservice.refreshtoken", response.body().toString())
                    tokens?.let { myStorage.saveAnything("username", it.username) }
                    tokens?.let { myStorage.saveAnything("firstName", it.firstName) }
                    tokens?.let { myStorage.saveAnything("lastName", it.lastName) }
                    Log.i("tokeny authservice.refreshtoken", tokens.toString())
                    tokens?.token?.let {
                        myStorage.saveToken(
                            it,
                            SecurePreferences.TokenType.AUTH
                        )
                    }
                    tokens?.refreshToken?.let {
                        myStorage.saveToken(
                            it,
                            SecurePreferences.TokenType.REFRESH
                        )
                    }
                    Log.i("debugtoken", tokens?.refreshToken.toString())
                    if (tokens?.token == null || tokens.refreshToken == null) {
                        Log.i("refreshtoken", "token clear")
                        myStorage.clearTokens()
                    }
                    callback(tokens, true)
                } else {
                    Log.i("refreshtoken", "idk why it happens")
                    callback(null, false)

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("request failed", "${t.message}")
                // myStorage.clearTokens()
                callback(null, false)
            }
        })

    }

    fun refreshTokenSynchronously(body: RefreshTokenRequest): LoginResponse? {
        return try {
            val response = authApi.refreshToken(body).execute()
            if (response.isSuccessful && response.body()?.token != null) {
                val tokens = response.body()
                tokens?.let { myStorage.saveAnything("username", it.username) }
                tokens?.let { myStorage.saveAnything("firstName", it.firstName) }
                tokens?.let { myStorage.saveAnything("lastName", it.lastName) }
                tokens?.token?.let {
                    myStorage.saveToken(it, SecurePreferences.TokenType.AUTH)
                }
                tokens?.refreshToken?.let {
                    myStorage.saveToken(it, SecurePreferences.TokenType.REFRESH)
                }
                if (tokens?.token == null || tokens.refreshToken == null) {
                    myStorage.clearTokens()
                }
                tokens
            } else {
                myStorage.clearTokens()
                null
            }
        } catch (t: Throwable) {
            Log.i("request failed", "${t.message}")
            myStorage.clearTokens()
            null
        }
    }
    override fun getAllServiceRequestsWithUserName(
        page: Int?,
        pageSize: Int?,
        callback: (List<ServiceRequestWithUserNameDtoResponse>) -> Unit
    ){
        val serviceRequestList: MutableList<ServiceRequestWithUserNameDtoResponse> = mutableListOf()
        Log.i("page",page.toString())
        Log.i("pagesize",pageSize.toString())
        authApi.getAllServiceRequestsWithUserName(page, pageSize)
            .enqueue(object : Callback<List<ServiceRequestWithUserNameDtoResponse>> {

                override fun onResponse(
                    call: Call<List<ServiceRequestWithUserNameDtoResponse>>,
                    response: Response<List<ServiceRequestWithUserNameDtoResponse>>
                )
                {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            serviceRequestList.addAll(body)
                        }

                        callback(serviceRequestList)
                    }
                }

                override fun onFailure(
                    call: Call<List<ServiceRequestWithUserNameDtoResponse>>,
                    t: Throwable
                ) {
                    Log.i("onFail->getAllServiceRequestsWithUserName","failed with reason ${t.message}")
                }
            }


            )

    }

    override fun getCustomerList(page: Int?,pageSize: Int?, callback: (List<Customer>) -> Unit) {
        val customerList: MutableList<Customer> = mutableListOf()
        authApi.getAllCustomers(page,pageSize).enqueue(object : Callback<List<Customer>>{
            override fun onResponse(
                call: Call<List<Customer>>,
                response: Response<List<Customer>>
            ) {
                Log.i("Customer response ",response.body().toString())
                if (response.isSuccessful) {

                    response.body()?.let { body ->
                        customerList.addAll(body)
                    }

                    callback(customerList)
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.i("onFail->getCustomerList","failed with reason ${t.message}")
            }
        })
    }

    override fun getServiceDetails(id: Long, callback: (ServiceRequestWithDetailsDto) -> Unit) {
Log.i("getservicedetails","id: $id")

        authApi.getServiceDetails(id)
            .enqueue(object : Callback<ServiceRequestWithDetailsDto> {
                override fun onResponse(
                    call: Call<ServiceRequestWithDetailsDto>,
                    response: Response<ServiceRequestWithDetailsDto>
                ) {
                    Log.i("getservicedetails","we did it into onresponse")
                    if (response.isSuccessful) {
                        Log.i("getservicedetails","response success and ${response.body().toString()}")
                        response.body()?.let { body ->
                            callback(body)
                        }

                    }
                    Log.i("getservicedetails","but we didnt catch response")
                    Log.i("getservicedetails","zwrotka : ${response.body().toString()}")
                }

                override fun onFailure(
                    call: Call<ServiceRequestWithDetailsDto>,
                    t: Throwable
                ) {
                    Log.i("onFail->getServiceDetails","failed with reason ${t.message}")
                }
            })
    }

    override fun deleteServiceById(id: Long, callback: (Boolean) -> Unit) {
        authApi.deleteServiceById(id)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        callback(true)
                    } else {
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("onFail->deleteServiceById","failed with reason ${t.message}")
                    callback(false)
                }
            })
    }


    override fun addStatusToService(
        serviceId: Long,
        body: StatusHistoryDtoRequest,
        callback: (StatusHistoryDtoRequest?,Boolean) -> Unit
    ) {
       authApi.addStatusToService(serviceId,body)
           .enqueue(object :Callback<StatusHistoryDtoRequest>
           {
               override fun onResponse(
                   call: Call<StatusHistoryDtoRequest>,
                   response: Response<StatusHistoryDtoRequest>
               ) {
                   if(response.isSuccessful&&response.body()!=null){
                       response.body()?.let { body ->
                           callback(body,true)
                   }
                   }else{
                       callback(null,false)
                   }
               }

               override fun onFailure(call: Call<StatusHistoryDtoRequest>, t: Throwable) {
                   Log.i("onFail->addStatusToService","failed with reason ${t.message}")
                   callback(null,false)
               }
           }
           )
    }

    override fun getCustomerWithDevicesByPhoneNumber(
        phoneNumber: Long,
        callback: (CustomerWithDevicesListDtoResponse?, Boolean) -> Unit
    ) {
        authApi.getUserWithDevicesByPhoneNumber(phoneNumber).enqueue(object :Callback<CustomerWithDevicesListDtoResponse>
        {

            override fun onResponse(
                call: Call<CustomerWithDevicesListDtoResponse>,
                response: Response<CustomerWithDevicesListDtoResponse>
            ) {


                if(response.code()==404){

                        callback(null,false)

                }
                    if(response.isSuccessful){

                        response.body()?.let { body ->

                            callback(body,true)
                        }
                    }else{
                        callback(null,true)
                    }

            }

            override fun onFailure(call: Call<CustomerWithDevicesListDtoResponse>, t: Throwable) {
                Log.i("onFail->getCustomerWithDevicesByPhoneNumber","failed with reason ${t.message}")
                callback(null,false)
            }
        }
        )
    }


    override fun addCustomerWithDeviceAndService(
        body: CustomerAndDevicesAndServiceRequestsDto,
        callback: (CustomerAndDevicesAndServiceResponseDto?,Boolean) -> Unit
    ) {
        authApi.addCustomerWithDeviceAndService(body)
            .enqueue(object : Callback<CustomerAndDevicesAndServiceResponseDto> {
                override fun onResponse(
                    call: Call<CustomerAndDevicesAndServiceResponseDto>,
                    response: Response<CustomerAndDevicesAndServiceResponseDto>
                ) {
                    Log.i("POST response",response.body().toString())
                    if(response.isSuccessful&&response.body()!=null){
                        val x =response.body()
                        callback(response.body(),true)
                    } else {
                        callback(null,false)
                    }
                }

                override fun onFailure(call: Call<CustomerAndDevicesAndServiceResponseDto>, t: Throwable) {
                    Log.i("onFail->deleteServiceById","failed with reason ${t.message}")
                    callback(null,false)
                }
            })
    }

    override fun editService(id: Long, body: ServiceRequest, callback: (Boolean) -> Unit) {
        authApi.editService(id,body)
            .enqueue(object :Callback<ServiceRequest>
            {
                override fun onResponse(
                    call: Call<ServiceRequest>,
                    response: Response<ServiceRequest>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let { body ->
                            Log.i("Edit body", body.toString())
                            callback(true)
                        }
                    }else{
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<ServiceRequest>, t: Throwable) {
                    Log.i("onFail->addStatusToService","failed with reason ${t.message}")
                    callback(false)
                }
            }
            )
    }
    override fun getDeviceList(page: Int?,pageSize: Int?, callback: (List<Device>) -> Unit) {
        val deviceList: MutableList<Device> = mutableListOf()
        authApi.getAllDevices(page,pageSize).enqueue(object : Callback<List<Device>>{
            override fun onResponse(
                call: Call<List<Device>>,
                response: Response<List<Device>>
            ) {
                Log.i("Device response ",response.body().toString())
                if (response.isSuccessful) {

                    response.body()?.let { body ->
                        deviceList.addAll(body)
                    }

                    callback(deviceList)
                }
            }

            override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                Log.i("onFail->getCustomerList","failed with reason ${t.message}")
            }
        })
    }

}