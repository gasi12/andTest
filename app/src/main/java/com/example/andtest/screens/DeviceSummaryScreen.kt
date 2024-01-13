package com.example.andtest.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.DeviceSummaryScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun DeviceSummaryScreen(viewModel: DeviceSummaryScreenViewModel, navController: NavController){
    Button(onClick ={ navController.navigate(Screen.REGISTER.name)}) {
        Text(text = "devicescreen")
    }

    var topBarQuery by viewModel.sharedViewModel.topBarQuery
    val devices by viewModel.sharedViewModel.devices.observeAsState(initial = emptyList())
    val isLastPageEmpty by viewModel.sharedViewModel.isLastPageEmptyDevices
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val itemsToDisplay = 100
    val initialDataLoaded by viewModel.isDataPresent
    val endOfListReached by derivedStateOf {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
    }
    val filteredDevices = devices.filter { it.deviceSerialNumber.toString().startsWith(topBarQuery)  }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached && initialDataLoaded == true && devices.isNotEmpty()) {
            Log.i("whos calling page?","Launcheffect")
            Log.i("filtered sr",filteredDevices.toString())
            coroutineScope.launch {
                if (topBarQuery.isEmpty() && filteredDevices.size < itemsToDisplay) {
                    viewModel.sharedViewModel.getAllDevices(40)
                } else if (topBarQuery.isNotEmpty() && !isLastPageEmpty!!) {
                    var shouldContinue = true
                    while(shouldContinue){
                        delay(400)
                        viewModel.sharedViewModel.getAllDevices(40)
                        if (filteredDevices.isNotEmpty()) {
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
        viewModel.sharedViewModel.refreshDevices()
        refreshing = false
        Log.i("device summary", "refreshing")
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
                            .padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        item {
                            Text(text = topBarQuery, textAlign = TextAlign.Center)
                        }
                        if(devices.isNullOrEmpty()){
                            item { CircularProgressIndicator() }
                        }
                        else {
                            items(filteredDevices.take(itemsToDisplay)) { device ->
                                var showDialog by remember { mutableStateOf(false) }
                                Box(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .padding(2.dp)
                                        .fillMaxSize()
                                        .background(
                                            MaterialTheme.colorScheme.secondaryContainer
                                                .copy(alpha = 0.3f),
                                            RoundedCornerShape(15.dp)
                                        )
                                        .combinedClickable(
                                            onLongClick = { showDialog = true },
                                            onClick = {
//                                                navController.navigate("${Screen.CUSTOMERDETAILS.name}/${device.phoneNumber}") //todo
                                            }),
                                    contentAlignment = Alignment.Center,
                                    propagateMinConstraints = true
                                ) {
                                    Column(modifier= Modifier.padding(10.dp)) {
                                        Text(
                                            text = "Device",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(0.dp),
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = device.deviceType.visibleName.plus(" ").plus(device.deviceName),
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(0.dp)
                                        )
                                        Text(
                                            text = "serial number",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(0.dp),
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = device.deviceSerialNumber,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(0.dp)
                                        )

                                    }
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

                                                    showDialog = false
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)


                                            ) {
                                                Text("Edit")
                                            }
                                            TextButton(
                                                onClick = {

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
