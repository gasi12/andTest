package com.example.andtest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.andtest.R
import com.example.andtest.api.dto.Customer
import com.example.andtest.components.AlertDialogExample
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.CustomerDevicesComponent
import com.example.andtest.components.CustomerInfoComponent
import com.example.andtest.components.DeviceItem
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.CustomerDetailsViewModel
import com.example.andtest.viewModels.ServiceDetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailsScreen(viewModel: CustomerDetailsViewModel, navController: NavController) {
    val customerWithDevices by viewModel.customerWithDevices.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    val isDeleted by viewModel.isDeleted
    LaunchedEffect(isDeleted){
        if(isDeleted)
            navController.navigateUp()
    }


    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Yellow)) {
        Scaffold (
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(Color.Yellow),
                    title = { Text("Customer details screen hopefully") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp()}) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content ={

                Column(modifier = Modifier.padding(it).padding(horizontal = 20.dp)) {
                    CustomerDevicesComponent(customerWithDevices = customerWithDevices, onDeviceClick ={} )
                }

                      
                }



        )

    }

}

