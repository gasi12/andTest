package com.example.andtest.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.andtest.api.dto.DeviceType
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.Status
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.ServicesSummaryScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ServicesSummaryScreen(viewModel: ServicesSummaryScreenViewModel, navController: NavController) {


    var topBarQuery by viewModel.sharedViewModel.topBarQuery
    val serviceRequests by viewModel.sharedViewModel.serviceRequests.observeAsState(initial = emptyList())
    val isLastPageEmpty by viewModel.sharedViewModel.isLastPageEmptyServices
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val itemsToDisplay = 100
    val initialDataLoaded by viewModel.isDataPresent
    val endOfListReached by derivedStateOf {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
    }
    val filteredServiceRequests = serviceRequests.filter { it.customerLastName.startsWith(topBarQuery)  }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached && initialDataLoaded == true && serviceRequests.isNotEmpty()) {
            Log.i("whos calling page?","Launcheffect")
            Log.i("filtered sr",filteredServiceRequests.toString())
            coroutineScope.launch {
                if (topBarQuery.isEmpty() && filteredServiceRequests.size < itemsToDisplay) {
                    viewModel.sharedViewModel.getAllServices(40)
                } else if (topBarQuery.isNotEmpty() && !isLastPageEmpty!!) {
                    var shouldContinue = true
                    while(shouldContinue){
                        delay(400)
                        viewModel.sharedViewModel.getAllServices(40)
                        if (filteredServiceRequests.isNotEmpty()) {
                            shouldContinue = false
                        }
                    }
                }
            }
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(500)
        viewModel.sharedViewModel.refreshServices()
            refreshing = false
            Log.i("Services summary", "refreshing")
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)

    Surface(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Scaffold(
            content = {
                Box(Modifier.pullRefresh(state)) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        item {
                            Text(text = topBarQuery, textAlign = TextAlign.Center)
                        }
                        if(serviceRequests.isNullOrEmpty()){
                            item { CircularProgressIndicator() }
                        }
                        else {
                            items(filteredServiceRequests.take(itemsToDisplay)) { serviceRequest ->
                                var showDialog by remember { mutableStateOf(false) }
                                Box(
                                    modifier = Modifier
//                                        .heightIn(max=200.dp)
                                        .padding(5.dp)
                                        .fillMaxSize()
                                        .background(
                                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                                            RoundedCornerShape(15.dp)
                                        )
                                        .combinedClickable(
                                            onLongClick = {
                                                Log.i("WFT",serviceRequest.toString())
//                                                showDialog = true
                                                          },
                                            onClick = { navController.navigate("${Screen.SERVICEDETAILS.name}/${serviceRequest.id}") }),
                                    contentAlignment = Alignment.Center,
                                    propagateMinConstraints = true
                                ) {
                                ServiceSummaryCardAlt(serviceRequest = serviceRequest)
                                }
                                if (showDialog) {
                                    Dialog(onDismissRequest = { showDialog = false }) {
                                        Column(
                                            modifier = Modifier
                                                .width(280.dp) // Set the width as per your requirement
                                                .height(200.dp) // Set the height as per your requirement
                                                .background(MaterialTheme.colorScheme.background)
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    navController.navigate("${Screen.ADDSTATUS.name}/${serviceRequest.id}")
                                                    showDialog = false
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)
                                            ) {
                                                Text("Add status")
                                            }
                                            TextButton(
                                                onClick = {
                                                          viewModel.sharedViewModel.sharedServiceRequestId.value=serviceRequest.id
                                                    viewModel.sharedViewModel.sharedServiceRequest.value= ServiceRequest(serviceRequest.description,serviceRequest.price);
                                                    navController.navigate("${Screen.EDITSERVICE.name}/${serviceRequest.id}")
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)


                                            ) {
                                                Text("Edit")
                                            }
                                            TextButton(
                                                onClick = {
                                                    viewModel.sharedViewModel.deleteById(serviceRequest.id)
                                                    showDialog = false
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)
                                            ) {
                                                Text("Delete")
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    }
                    PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

                }
            }, floatingActionButton = {
                FloatingActionButton(onClick = {navController.navigate(Screen.ADDSERVICE.name) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        )
    }
}
fun formatDateToShortLocaleToString(date: LocalDateTime):String{

    val locale: Locale = Locale.getDefault()
    return date.dayOfMonth.toString().plus(" ").plus(date.month.getDisplayName(java.time.format.TextStyle.SHORT,locale))

}
@Preview
@Composable
fun CardPreview(){
    val serviceRequest = ServiceRequestWithUserNameDtoResponse(
        id = 1337L,
        lastStatus = Status.PENDING.name,
        description = "Service description",
        endDate = LocalDateTime.now().plusDays(5),
        startDate = LocalDateTime.now(),
        price = (80L)/3,
        customerFirstName = "Json",
        customerLastName = "Wick",
        userId = 5L,
        customerId =7L,
        customerPhoneNumber = "1234535",
        deviceName = "Dell",
        deviceType = DeviceType.DESKTOP

    )
ServiceSummaryCardAlt(serviceRequest = serviceRequest)
}
@Composable
fun ServiceSummaryCard(serviceRequest: ServiceRequestWithUserNameDtoResponse){
    Column(modifier= Modifier.padding(10.dp)) {
        Row {
            Text(
                text = "Customer",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = formatDateToShortLocaleToString(serviceRequest.startDate),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = serviceRequest.customerFirstName.plus(" ").plus(serviceRequest.customerLastName),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(0.dp)
        )
//                                        RowWithValue(row =serviceRequest.deviceType.visibleName.plus(" ").plus(serviceRequest.deviceName) , description = "Device")
        Text(
            text = "Device",
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(0.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = serviceRequest.deviceType.visibleName.plus(" ").plus(serviceRequest.deviceName),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(0.dp)
        )
        Text(
            text = "Description",
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(0.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = serviceRequest.description,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .padding(0.dp)
                .heightIn(max = 50.dp), // Set your desired maximum height here
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Composable
fun ServiceSummaryCardAlt(serviceRequest: ServiceRequestWithUserNameDtoResponse){
    Column(modifier= Modifier
        .padding(15.dp)
//        .padding(vertical = 5.dp)

        ) {
        Row {
            RowWithValue(row =serviceRequest.customerFirstName.plus(" ").plus(serviceRequest.customerLastName) , description = "Customer")
            Spacer(Modifier.weight(1f))
            Text(
                text = formatDateToShortLocaleToString(serviceRequest.startDate),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        RowWithValue(row =serviceRequest.deviceName , description = "Device")
        RowWithValue(row = serviceRequest.description, description ="Description",overflow = true )
    }
}