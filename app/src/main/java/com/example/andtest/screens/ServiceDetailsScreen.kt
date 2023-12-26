package com.example.andtest.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.api.service.MockService
import com.example.andtest.components.AlertDialogExample
import com.example.andtest.viewModels.ServiceDetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailsScreen(viewModel: ServiceDetailsViewModel, navController: NavController) {
    val serviceRequest by viewModel.serviceRequest.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Yellow)) {
     Scaffold (
         topBar = {
             TopAppBar(
                 modifier = Modifier.background(Color.Yellow),
                 title = { Text("Service details screen hopefully") },
                 colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.LightGray),
                 navigationIcon = {
                     IconButton(onClick = { navController.navigateUp()}) {
                         Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                     }
                 }
             )
         },
         content ={Box(modifier = Modifier.padding(top = 65.dp)){
             Box(modifier =Modifier.fillMaxSize() ){
Column {
Text(text = serviceRequest.toString())
    Button(onClick = {
    showDialog.value = true

    }) {
        Text(text = "Delete")
    }
    if(showDialog.value) {

        AlertDialogExample(
            onDismissRequest = { showDialog.value=false},
            onConfirmation = { },
            dialogTitle ="Confirm" ,
            dialogText ="CYCKI" ,
            icon = Icons.Default.Favorite
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
fun ExampleScreenPreview2(){
    ServiceDetailsScreen(
        viewModel = ServiceDetailsViewModel(authService = MockService(),1L),
        navController = rememberNavController()
    )
}