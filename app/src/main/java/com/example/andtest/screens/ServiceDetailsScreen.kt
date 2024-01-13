package com.example.andtest.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
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
import com.example.andtest.api.dto.ServiceRequestWithDetailsDto
import com.example.andtest.api.dto.Status
import com.example.andtest.api.dto.StatusHistory
import com.example.andtest.components.AlertDialogExample
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.CustomerInfoComponent
import com.example.andtest.components.DeviceItem
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.components.shimmerEffect
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.MockViewModel
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
                Box(modifier =Modifier.fillMaxWidth() ){
                    Column (Modifier.verticalScroll(rememberScrollState())){
                        Box(){
                         SummaryList(serviceRequest)
                        }
                        ButtonComponent(labelValue = "Delete",
                            onClick = {
                            showDialog.value = true
                        })
                        ButtonComponent(labelValue = "Edit", onClick = {

                            viewModel.shareServiceRequest(serviceRequest?.price ?: 0, serviceRequest?.description ?: "")

                            navController.navigate("${Screen.EDITSERVICE.name}/${serviceRequest?.id}")

                        })
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

            }

        )

    }

}
@Preview
@Composable
fun RowWithValuePreview(){
    RowWithValue(row = "ogien", description ="pizda")
}
@Composable
fun SummaryList(serviceRequest: ServiceRequestWithDetailsDto? = ServiceRequestWithDetailsDto(
    id = 1L,
    customer = Customer(1, firstName = "john","wick",123456677),
    price = 12345,
    startDate = LocalDateTime.now(),
    endDate = LocalDateTime.now().plusDays(4),
    device = Device(5,"Car","1232456", DeviceType.DESKTOP),
    description = "that some long desciption to fill as many space as possible. This normally doesnt get any much longer than this," +
            " but keep in mind to make a bit more space for special occasion",
    lastStatus = Status.PENDING,
    statusHistoryList = listOf(
        StatusHistory(1L,"PENDING","Request created","2023-11-11"),
        StatusHistory(42L,"ON_HOLD","Keep in mind these id's ain't incrementing by 1","2023-11-12"),
        StatusHistory(45L,"Finished","blah blah blah","2023-11-13"),
        StatusHistory(47L,"Finished","blah blah blah","2023-11-13"),
    )
)
){

    Column(modifier = Modifier

        .fillMaxWidth()) {
        Column(modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(10.dp)) {
            RowWithValue(row = serviceRequest?.description.toString(), description ="Description")
            RowWithValue(row = serviceRequest?.price.toString(), description ="Price" )
            RowWithValue(row = serviceRequest?.startDate?.dayOfWeek?.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()), description ="Start date" )
            if(!serviceRequest?.endDate?.toString().isNullOrEmpty()){

                RowWithValue(row = serviceRequest?.endDate?.dayOfWeek?.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()), description ="End date" )
            }
            RowWithValue(row = serviceRequest?.lastStatus?.visibleName.toString(), description ="Last status" )


        }
  Column(modifier = Modifier
      .background(Color.Gray)
      .fillMaxWidth()
      .padding(10.dp)){
     RowWithValue(row = serviceRequest?.device?.deviceType?.visibleName.plus(" ").plus(serviceRequest?.device?.deviceName.toString()), description ="Device name" )
      RowWithValue(row = serviceRequest?.device?.deviceSerialNumber.toString(), description ="Serial number" )
}

DeviceItem(deviceName = serviceRequest?.device?.deviceName.toString(), deviceType =serviceRequest?.device?.deviceType?.visibleName.toString() , deviceSerialNumber =serviceRequest?.device?.deviceSerialNumber.toString() ) {
    
}
CustomerInfoComponent(firstName = serviceRequest?.customer?.firstName.toString(), lastName = serviceRequest?.customer?.lastName.toString(), phoneNumber =serviceRequest?.customer?.phoneNumber.toString() )

        Text(text = "Status History")
        serviceRequest?.statusHistoryList?.forEach { statusHistory ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color(25, 118, 50))) {
                Text(text = "ID")
                Text(text = statusHistory.id.toString())
                Text(text = "Status")
                Text(text = statusHistory.status.toString())
                Text(text = "Description")
                Text(text = statusHistory.comment.toString())
                Text(text = "Date")
                Text(text = statusHistory.time.toString())
            }

        }
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
            modifier = Modifier.padding(bottom = 0.dp), // Reduced bottom padding
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
