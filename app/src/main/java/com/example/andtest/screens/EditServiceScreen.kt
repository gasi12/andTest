package com.example.andtest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.api.dto.ServiceRequestEditor
import com.example.andtest.api.service.MockService
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.InputNumberField
import com.example.andtest.components.MultiLineInputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.viewModels.EditServiceScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditService(navController: NavController, viewModel: EditServiceScreenViewModel) {
    val serviceRequest by viewModel.sharedViewModel.sharedServiceRequestEditor.observeAsState()
    val serviceRequestId by viewModel.sharedViewModel.sharedServiceRequestId.observeAsState()
    val id= remember { mutableStateOf(0L) }
    val price = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val isSentSuccessfully by viewModel.isSentSuccessfully
    val isLoading = remember { mutableStateOf(false)}
    LaunchedEffect(serviceRequest) {
        serviceRequest?.let {
            price.value = it.price.toString()
            description.value = it.description
        }
        serviceRequestId?.let {
            id.value=it
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
Scaffold(
    topBar = {
        TopAppBar(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            title = { Text(stringResource(id = R.string.editService)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp()}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }

) {
    Column(modifier = Modifier.fillMaxWidth().padding(it).padding(20.dp)) {
//            NormalTextComponent(value = stringResource(id = R.string.hello))
//        BoldTextComponent(value = stringResource(id = R.string.editService))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp))



        InputNumberField(labelValue = stringResource(id = R.string.price), data = price)
        MultiLineInputTextField(labelValue = stringResource(id = R.string.description), data = description, maxLines = 2)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp))
        ButtonComponent(
            labelValue = stringResource(id = R.string.submit ), onClick = {
                isLoading.value=true
                price.value = price.value.let { if (it == "") "0" else it }
                viewModel.editService(
                    ServiceRequestEditor(description.value,price.value.toLong())
                )
            }
        )
        if(isLoading.value){
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        }
    }
}



        LaunchedEffect(isSentSuccessfully) {
            when (isSentSuccessfully) {
                true -> {
                    viewModel.sharedViewModel.refreshServices()
                    navController.navigateUp()
                    isLoading.value=false
                }
                false ->
                {
                    isLoading.value=false
                }
                null -> {
                }
            }
        }


    }
}
@Preview
@Composable
fun EditServicePreview() {
    EditService(navController = rememberNavController(), viewModel = EditServiceScreenViewModel(
        MockService(),1L, viewModel())
    )
}