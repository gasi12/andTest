package com.example.andtest.screens

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.andtest.viewModels.ExampleScreenViewModel

@Composable

fun ExampleScreen(viewModel: ExampleScreenViewModel,navController: NavController) {
    val serviceRequests by viewModel.serviceRequests.observeAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.getAllServices()
    }
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Main screen or sth", textAlign = TextAlign.Center)

            // Fetched data is now available in serviceRequests
            serviceRequests.forEach { serviceRequest ->
                Button( modifier = Modifier.padding(10.dp),shape = RoundedCornerShape(10.dp),onClick = { navController.navigate("Ex2") }) {
                    Text(
                        text = serviceRequest.toString()
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(Color((0.3F * serviceRequest.id), 0.6F, 0.3F)))
                }

            }


        }
    }
}
@Preview
@Composable
fun ExampleScreenPreview(){
    ExampleScreen(viewModel = ExampleScreenViewModel(authService = MockService()),navController= rememberNavController())
}