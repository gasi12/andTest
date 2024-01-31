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
import androidx.compose.foundation.layout.widthIn
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
import com.example.andtest.components.CustomerCardAlt

import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.CustomerSummaryScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CustomerSummaryScreen(viewModel: CustomerSummaryScreenViewModel, navController: NavController){
    Button(onClick ={ navController.navigate(Screen.REGISTER.name)}) {
        Text(text = "customerscreen")
    }

    var topBarQuery by viewModel.sharedViewModel.topBarQuery
    val customers by viewModel.sharedViewModel.customers.observeAsState(initial = emptyList())
    val isLastPageEmpty by viewModel.sharedViewModel.isLastPageEmptyCustomers
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val itemsToDisplay = 100
    val initialDataLoaded by viewModel.isDataPresent
    val endOfListReached by derivedStateOf {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
    }
    val filteredCustomers = customers.filter { it.phoneNumber.toString().startsWith(topBarQuery)  }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached && initialDataLoaded == true && customers.isNotEmpty()) {
            Log.i("whos calling page?","Launcheffect")
            Log.i("filtered sr",filteredCustomers.toString())
            coroutineScope.launch {
                if (topBarQuery.isEmpty() && filteredCustomers.size < itemsToDisplay) {
                    viewModel.sharedViewModel.getAllCustomers(40)
                } else if (topBarQuery.isNotEmpty() && !isLastPageEmpty!!) {
                    var shouldContinue = true
                    while(shouldContinue){
                        delay(400)
                        viewModel.sharedViewModel.getAllCustomers(40)
                        if (filteredCustomers.isNotEmpty()) {
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
        viewModel.sharedViewModel.refreshCustomers()
        refreshing = false
        Log.i("customer summary", "refreshing")
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
                        if(customers.isNullOrEmpty()){
                            item { CircularProgressIndicator() }
                        }
                        else {
                            items(filteredCustomers.take(itemsToDisplay)) { customer ->
                                var showDialog by remember { mutableStateOf(false) }
                                Box(
                                    modifier = Modifier
//                                        .height(200.dp)
                                        .padding(5.dp)
//                                        .fillMaxSize()
                                        .widthIn(max = 500.dp)
                                        .background(
                                            MaterialTheme.colorScheme.secondaryContainer
                                                .copy(alpha = 0.3f),
                                            RoundedCornerShape(15.dp)
                                        )
                                        .combinedClickable(
                                            onLongClick = { showDialog = true },
                                            onClick = {
                                                navController.navigate("${Screen.CUSTOMERDETAILS.name}/${customer.phoneNumber}")
                                            }),
                                    contentAlignment = Alignment.Center,
                                    propagateMinConstraints = true
                                ) {
                                    CustomerCardAlt(customer = customer)
//                                    Column(modifier= Modifier.padding(10.dp)) {
//                                        Text(
//                                            text = "Customer",
//                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                                            modifier = Modifier.padding(0.dp),
//                                            fontWeight = FontWeight.Bold,
//                                        )
//                                        Text(
//                                            text = customer.firstName.plus(" ").plus(customer.lastName),
//                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                                            modifier = Modifier.padding(0.dp)
//                                        )
//                                        Text(
//                                            text = "Phone number",
//                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                                            modifier = Modifier.padding(0.dp),
//                                            fontWeight = FontWeight.Bold,
//                                        )
//                                        Text(
//                                            text = customer.phoneNumber.toString(),
//                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                                            modifier = Modifier.padding(0.dp)
//                                        )
//
//                                    }
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
