package com.example.andtest.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.api.BASE_URL
import com.example.andtest.api.downloader.AndroidDownloader
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.CustomerAndDevicesAndServiceRequestsDto
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.DeviceType
import com.example.andtest.api.dto.ServiceRequestEditor
import com.example.andtest.api.service.MockService
import com.example.andtest.components.AlertDialogExample
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.CustomerDevicesIcons
import com.example.andtest.components.DropDownTextField
import com.example.andtest.components.InputNumberField
import com.example.andtest.components.InputTextField
import com.example.andtest.components.LastInputNumberField
import com.example.andtest.components.LastInputTextField
import com.example.andtest.components.MultiLineInputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.viewModels.AddServiceScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddServiceScreen(navController: NavController, viewModel: AddServiceScreenViewModel) {
    val firstName = remember { mutableStateOf("") }
    val firstNameError = remember { mutableStateOf(false) }
    val lastName = remember { mutableStateOf("") }
    val lastNameError = remember { mutableStateOf(false) }
    val phoneNumber = remember { mutableStateOf("") }
    val phoneNumberError = remember { mutableStateOf(false) }
    val description = remember { mutableStateOf("") }
    val descriptionError = remember { mutableStateOf(false) }
    val deviceName = remember { mutableStateOf("") }
    val deviceNameError = remember { mutableStateOf(false) }
    val deviceType = remember { mutableStateOf("") }
    val deviceTypeError = remember { mutableStateOf(false) }
    val deviceSerialNumber = remember { mutableStateOf("") }
    val deviceSerialNumberError= remember { mutableStateOf(false) }
    val statusPlaceholder = if(deviceType.value=="")"device type" else DeviceType.valueOf(deviceType.value).visibleName
    val price = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val isDataPresent by viewModel.isDataPresent
    val isSentSuccessfully by viewModel.isSentSuccessfully
    val skipPage = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = {4})
    val focusManager = LocalFocusManager.current
    var isExpanded  by remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }
    val navigationSide = remember { mutableStateOf(false) }
    val showAlert = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val downloader = AndroidDownloader(context)
    val createdServiceId by viewModel.createdServiceId
    val customerWithDevices by viewModel.customerWithDevices.observeAsState(
        initial = CustomerWithDevicesListDtoResponse(
            0L,
            "",
            "",
            0L,
            listOf()
        )
    )

    val fields = listOf(
        firstName to firstNameError,
        lastName to lastNameError,
        phoneNumber to phoneNumberError,
        description to descriptionError,
        deviceName to deviceNameError,
        deviceSerialNumber to deviceSerialNumberError
    )


    fields.forEach { (field, error) ->
        LaunchedEffect(field.value) {
            if (field.value.isNotEmpty()) {
                error.value = false
            }
        }
    }
    LaunchedEffect(key1 = isSentSuccessfully) {
        if(isSentSuccessfully == true){
           showAlert.value=true
        }
    }
   if(showAlert.value)
   {
       AlertDialogExample(
           onDismissRequest = {
               showAlert.value=false
               navController.popBackStack()
                              },
           onConfirmation = { showAlert.value=false
               Log.i("response from post","to id  ${BASE_URL}services/service/${createdServiceId}/report")
               downloader.downloadFile("${BASE_URL}services/service/$createdServiceId/report",createdServiceId?:-1)
                                navController.popBackStack()},
           dialogTitle ="Alert" ,
           dialogText = "Download confirmation?",
           icon = Icons.Default.Info
       )}



    BackHandler(onBack = {
        navigationSide.value=false
        if (pagerState.currentPage==3&&skipPage.value) {//if device is selected from list then make back button
            coroutineScope.launch {     // go back to device selection, not edit
                pagerState.animateScrollToPage(1)
            }
        }else
        if (pagerState.currentPage == 1) {// clear phone number fetch state
            viewModel.clearIsDataPresent()
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)

            }
        }else
        if (pagerState.currentPage > 0) { // if not on the first page, move to the previous page

            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }

        }
        else {
            navController.popBackStack() //if on the first page, exit the screen
        }
    })
fun getUserByPhone(){
    if(phoneNumber.value.isNotEmpty()){
        navigationSide.value=true
        viewModel.getUserWithDevicesByPhoneNumber(phoneNumber.value.toLong())
        skipPage.value=false
        focusManager.clearFocus()
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }else{
        phoneNumberError.value=phoneNumber.value.isEmpty()
    }
}


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            userScrollEnabled = false
        ) { page ->
            when (page) {

                0 -> {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        item {
                            NormalTextComponent(value = stringResource(id = R.string.hello))
                            BoldTextComponent(value = stringResource(id = R.string.customerInfo))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                            )

                            LastInputNumberField(
                                labelValue = stringResource(id = R.string.phoneNumber),
                                data = phoneNumber,
                                error =  phoneNumberError.value ,
                                onEnterPressed = {
                                    getUserByPhone()
                                }
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                            ButtonComponent(
                                labelValue = "Next", onClick = {
                                    getUserByPhone()
                                }
                            )
                        }
                    }
                }

                1 -> {
                    when (isDataPresent) {
                        true -> {
                            //set items for add request
                            firstName.value= customerWithDevices?.firstName ?:""
                            lastName.value=customerWithDevices?.lastName?:""
                            phoneNumber.value=customerWithDevices?.phoneNumber.toString()
Column {
    CustomerDevicesIcons(customerWithDevices = customerWithDevices, onDeviceClick =
    {
        skipPage.value = true
        deviceName.value = it.deviceName
        deviceSerialNumber.value =
            it.deviceSerialNumber
        deviceType.value = it.deviceType.toString()
        focusManager.clearFocus()
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 2)
        }
    }
    )
    Spacer(modifier = Modifier.height(20.dp))
    ButtonComponent(labelValue = stringResource(id = R.string.addNewDevice), onClick =
    {skipPage.value=false
        navigationSide.value=true
        coroutineScope.launch {

            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    })

}



                        }
                        false -> {

                            LazyColumn()
                            {
                                item {
                                    if(navigationSide.value) {
                                        LaunchedEffect(Unit) {
                                            focusRequester.requestFocus()
                                        }
                                    }
                                    InputTextField(
                                        labelValue = stringResource(id = R.string.firstName),
                                        data = firstName,
                                        error = firstNameError.value,
                                        modifier = if(navigationSide.value) Modifier.focusRequester(focusRequester) else Modifier
                                    )
                                    LastInputTextField(
                                        labelValue = stringResource(id = R.string.lastName),
                                        data = lastName,
                                        error = lastNameError.value,
                                        onEnterPressed = {
                                            if (firstName.value.isNotEmpty() && lastName.value.isNotEmpty()) {
                                                navigationSide.value=true
                                                focusManager.clearFocus()
                                                coroutineScope.launch {
                                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                                }
                                            } else {
                                                firstNameError.value = firstName.value.isEmpty()
                                                lastNameError.value = lastName.value.isEmpty()
                                            }
                                        }
                                    )
                                    InputTextField(
                                        labelValue = stringResource(id = R.string.phoneNumber),
                                        data = phoneNumber,
                                        readOnly = true,

                                    )
                                    ButtonComponent(labelValue = stringResource(id = R.string.addNewCustomer), onClick =
                                    {
                                        if (firstName.value.isNotEmpty() && lastName.value.isNotEmpty()) {
                                            navigationSide.value=true

                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                            }
                                            focusManager.clearFocus()
                                        } else {
                                            firstNameError.value = firstName.value.isEmpty()
                                            lastNameError.value = lastName.value.isEmpty()
                                        }
                                    })
                                }
                            }
                        }

                        else -> {
                            CircularProgressIndicator()


                        }
                    }
                }
                2 -> {
                                        LazyColumn(modifier = Modifier.fillMaxWidth()) {

                        item {
                            NormalTextComponent(value = stringResource(id = R.string.hello))
                            BoldTextComponent(value = stringResource(id = R.string.addNewDevice))
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp))


                            ExposedDropdownMenuBox(
                                expanded =isExpanded ,
                                onExpandedChange ={
                                    isExpanded=it
                                    error.value=false
                                } ) {
                                DropDownTextField(labelValue = "Device type", placeholder =statusPlaceholder , modifier = Modifier.menuAnchor(), data =deviceType , expanded =isExpanded, error =deviceTypeError.value  )
                                ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded=false}) {
                                    DeviceType.values().forEach { statusValue->
                                        DropdownMenuItem(
                                            text = { Text(text = statusValue.visibleName) },
                                            onClick = {
                                                deviceType.value=statusValue.name
                                                isExpanded=false})

                                    }
                                }
                            }
                            InputTextField(labelValue = stringResource(id = R.string.deviceSerialNumber), data = deviceSerialNumber,error = deviceSerialNumberError.value)
                            LastInputTextField(labelValue = stringResource(id = R.string.deviceName), data = deviceName, error = deviceNameError.value,
                                onEnterPressed = {
                                    navigationSide.value=true
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                })
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp))


                            ButtonComponent(
                                labelValue = stringResource(id = R.string.submit) , onClick = {
                                    if(deviceSerialNumber.value.isNotEmpty()&&deviceName.value.isNotEmpty()){
                                        navigationSide.value=true
                                        focusManager.clearFocus()
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                        }
                                    }
                                    else{
                                        deviceNameError.value = deviceName.value.isEmpty()
                                        deviceSerialNumberError.value = deviceSerialNumber.value.isEmpty()
                                    }
                                }
                            )
                        }
                    }
                }
                3 -> {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        item {
                            NormalTextComponent(value = stringResource(id = R.string.hello))
                            BoldTextComponent(value = stringResource(id = R.string.serviceInfo))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                            )

                                if(navigationSide.value&&pagerState.currentPage==3) {
                                    LaunchedEffect(Unit) {
                                        focusRequester.requestFocus()
                                    }
                                }
                            InputNumberField(
                                labelValue = stringResource(id = R.string.price),
                                data = price,
                                modifier =  if(navigationSide.value) Modifier.focusRequester(focusRequester) else Modifier
                            )
                            MultiLineInputTextField(
                                labelValue = stringResource(id = R.string.description),
                                data = description,
                                maxLines = 4,
                                error = descriptionError.value
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                            )
                            ButtonComponent(
                                labelValue = stringResource(id = R.string.submit), onClick = {
                                    price.value = price.value.let { if (it == "") "0" else it }
                        if(description.value.isNotEmpty()&&deviceName.value.isNotEmpty()&&deviceSerialNumber.value.isNotEmpty()){
                        viewModel.addCustomerWithDeviceAndService(
                            CustomerAndDevicesAndServiceRequestsDto(
                                Customer(
                                    0,
                                    firstName.value,
                                    lastName.value,
                                    phoneNumber.value.toLong()
                                ),
                                Device(
                                    0,
                                    deviceName.value,
                                    deviceSerialNumber.value,
                                    DeviceType.valueOf(deviceType.value)
                                ),
                                ServiceRequestEditor(
                                    description.value,
                                    price.value.toLong()
                                )
                            )
                        )
                    }
                                    else{
                                    descriptionError.value=description.value.isEmpty()

                                    }



                                }
                            )

                        }
                    }
                }
            }
        }

    }
}


//@PreviewScreenSizes
@Preview
@Composable
fun AddServiceScreenPreview() {
    AddServiceScreen(
        navController = rememberNavController(),
        viewModel = AddServiceScreenViewModel(MockService(), viewModel())
    )
}

