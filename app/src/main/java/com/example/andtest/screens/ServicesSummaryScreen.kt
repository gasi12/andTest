package com.example.andtest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.api.service.MockService
import com.example.andtest.viewModels.ServicesSummaryScreenViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import com.example.andtest.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ServicesSummaryScreen(viewModel: ServicesSummaryScreenViewModel, navController: NavController) {
    val serviceRequests by viewModel.serviceRequests.observeAsState(initial = emptyList())

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(500)
        viewModel.getAllServices {
            refreshing = false
        }
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)

    var dataLoaded by rememberSaveable { mutableStateOf(false) }

    if (!dataLoaded) {
        LaunchedEffect(key1 = true) {
            viewModel.getAllServices {}
            dataLoaded = true
        }
    }


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
                            .padding(horizontal = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Text(text = "Main screen or sth", textAlign = TextAlign.Center)
                        }
                        items(serviceRequests) { serviceRequest ->
                            var showDialog by remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize()
                                    .background(Color.LightGray)
                                    .combinedClickable(
                                        onLongClick = { showDialog = true },
                                        onClick = { navController.navigate("${Screen.DETAILS.name}/${serviceRequest.id}") }),
                                contentAlignment = Alignment.Center,
                                propagateMinConstraints = true
                            ) {
                                Text(
                                    text = serviceRequest.toString(),
                                    color = Color(
                                        (0.05F * serviceRequest.id),
                                        .5F,
                                        .5F
                                    )
                                )
                            }
                            if (showDialog) {
                                Dialog(onDismissRequest = { showDialog = false }) {
                                    Column(
                                        modifier = Modifier
                                            .width(280.dp) // Set the width as per your requirement
                                            .height(200.dp) // Set the height as per your requirement
                                            .background(MaterialTheme.colors.surface)
                                    ) {
                                        TextButton(
                                            onClick = {
                                                navController.navigate("${Screen.ADDSTATUS.name}/${serviceRequest.id}")
                                                showDialog=false
                                                      },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        ) {
                                            Text("Add status")
                                        }
                                        TextButton(
                                            onClick = { /* Handle Load click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)


                                        ) {
                                            Text("Edit")
                                        }
                                        TextButton(
                                            onClick = {
                                                viewModel.deleteById(serviceRequest.id)
                                                      showDialog=false },
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
    @Preview
    @Composable
    fun ExampleScreenPreview() {
        ServicesSummaryScreen(
            viewModel = ServicesSummaryScreenViewModel(authService = MockService()),
            navController = rememberNavController()
        )
    }
