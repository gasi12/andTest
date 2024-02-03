package com.example.andtest.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.DeviceType
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.Status
import com.example.andtest.api.dto.StatusHistory
import com.example.andtest.components.AlertDialogExample
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.CustomerInfoIcons
import com.example.andtest.components.DeviceItem
import com.example.andtest.components.DeviceItemIcons
import com.example.andtest.components.ServiceRequestIcons
import com.example.andtest.components.StatusHistoryIcons
import com.example.andtest.components.shimmerEffect
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.ServiceDetailsViewModel
import java.time.LocalDateTime
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailsScreen(viewModel: ServiceDetailsViewModel, navController: NavController) {
    val serviceRequest by viewModel.serviceRequest.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    val isDeleted by viewModel.isDeleted
    val isInit by viewModel.isInit
    LaunchedEffect(isDeleted){
        if(isDeleted)
            navController.navigateUp()
    }
    LaunchedEffect(Unit){
        if(isInit)
      viewModel.getServiceDetails()
    }


    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Yellow)) {
        Scaffold (
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    title = { Text("Service details screen hopefully") },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp()}) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content ={Box(modifier = Modifier.padding(top = 64.dp)){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp) ){





                    LazyColumn {
                        item {     ServiceRequestIcons(
                            ServiceRequest(
                                serviceRequest?.id ?: 0,
                                serviceRequest?.description ?: "",
                                serviceRequest?.lastStatus ?: Status.PENDING,
                                serviceRequest?.endDate,
                                serviceRequest?.startDate ?: LocalDateTime.now(),
                                serviceRequest?.price ?: 0
                            ),
                            primaryColor = true
                        ) }

                        item {
                            DeviceItemIcons(
                                deviceName = serviceRequest?.device?.deviceName.toString(),
                                deviceType = serviceRequest?.device?.deviceType?.visibleName.toString(),
                                deviceSerialNumber = serviceRequest?.device?.deviceSerialNumber.toString(),
                                {},
                                false
                            )
                        }
                        item {
                            CustomerInfoIcons(
                                firstName = serviceRequest?.customer?.firstName.toString(),
                                lastName = serviceRequest?.customer?.lastName.toString(),
                                phoneNumber = serviceRequest?.customer?.phoneNumber.toString(),
                                false
                            )
                        }
                        items(serviceRequest?.statusHistoryList?: listOf()) { statusList ->
                            StatusHistoryIcons(statusHistory = statusList, primaryColor = false)

                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                ButtonComponent(
                                    labelValue = "Delete",
                                    onClick = {
                                        showDialog.value = true
                                    }, modifier = Modifier.weight(1f)
                                )
                                ButtonComponent(
                                    labelValue = "Edit",
                                    onClick = {
                                        viewModel.shareServiceRequest(serviceRequest?.price ?: 0, serviceRequest?.description ?: "")
                                        navController.navigate("${Screen.EDITSERVICE.name}/${serviceRequest?.id}")
                                    }, modifier = Modifier.weight(1f)
                                )
                            }


                        }

                    }


                        if(showDialog.value) {

                            AlertDialogExample(
                                onDismissRequest = { showDialog.value=false},
                                onConfirmation = {showDialog.value=false
                                    viewModel.deleteThisServiceRequest()},
                                dialogTitle ="Do you want to delete this service request?" ,
                                dialogText ="" ,
                                icon = Icons.Default.Warning
                            )
                        }

                }

            }

            }

        )

    }

}
//@Preview
//@Composable
//fun RowWithValuePreview(){
//SummaryList(ServiceRequestWithDetailsDto(
//        id = 1L,
//    customer = Customer(1, firstName = "john","wick",123456677),
//    price = 12345,
//    startDate = LocalDateTime.now(),
//    endDate = LocalDateTime.now().plusDays(4),
//    device = Device(5,"Car","1232456", DeviceType.DESKTOP),
//    description = "that some long desciption to fill as many space as possible. This normally doesnt get any much longer than this," +
//            " but keep in mind to make a bit more space for special occasion",
//    lastStatus = Status.PENDING,
//    statusHistoryList = listOf(
//        StatusHistory(1L,"PENDING","Request created","2023-11-11"),
//        StatusHistory(42L,"ON_HOLD","Keep in mind these id's ain't incrementing by 1","2023-11-12"),
//        StatusHistory(45L,"Finished","blah blah blah","2023-11-13"),
//        StatusHistory(47L,"Finished","blah blah blah","2023-11-13"),
//    )
//    ))
//}
@Composable
fun SummaryList(
    serviceRequest: ServiceRequestWithDetailsDto
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

    }

}
@Composable
fun RowWithValue(row: String?, description: String, modifier: Modifier = Modifier, overflow: Boolean = false) {
    Column(modifier= Modifier.padding(vertical = 2.dp) ) {
        Text(style = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
            text = description,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 0.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4F)
        )

        Text(style = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
            text = row ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = if (row?.startsWith("null") == true) Modifier
                .shimmerEffect()
                .alpha(0.0f) else Modifier.heightIn(max = 50.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            overflow = if (overflow) TextOverflow.Ellipsis else TextOverflow.Visible
        )
    }
}
