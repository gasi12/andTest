package com.example.andtest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.ServiceDetailsViewModel
import com.example.andtest.viewModels.ServicesSummaryScreenFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ExampleScreen(viewModel: ServicesSummaryScreenViewModel, navController: NavController) {
    val serviceRequests by viewModel.serviceRequests.observeAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.getAllServices()
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Scaffold(

            content = {

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
                        Button(

                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxSize(),
colors = ButtonDefaults.buttonColors(Color((0.2F * serviceRequest.id), .5F, .5F)),

                            onClick = { navController.navigate("${Screen.EX2.name}/${serviceRequest.id}") }) {
                            Text(
                                text = serviceRequest.toString()
                            )

                        }
                    }
                }
            }, floatingActionButton = {
                FloatingActionButton(onClick = { }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }

        )


    }
}
    @Preview
    @Composable
    fun ExampleScreenPreview() {
        ExampleScreen(
            viewModel = ServicesSummaryScreenViewModel(authService = MockService()),
            navController = rememberNavController()
        )
    }
