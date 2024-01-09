package com.example.andtest.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.api.dto.Status
import com.example.andtest.api.dto.StatusHistoryDtoRequest
import com.example.andtest.api.service.MockService
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.DropDownTextField
import com.example.andtest.components.MultiLineInputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.viewModels.AddStatusScreenViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddStatusScreen(navController: NavController, viewModel: AddStatusScreenViewModel) {
    val price = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }

    val status = remember { mutableStateOf("") }
    val statusPlaceholder = if(status.value=="")"status" else Status.valueOf(status.value).visibleName
var isExpanded by remember {

    mutableStateOf(false)
}
    val isBodyPresent by viewModel.isBodyPresent.observeAsState(false)
    val error = remember { mutableStateOf(false) }
if(isBodyPresent){
    navController.popBackStack()
}



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {



                    Column(modifier = Modifier.fillMaxWidth()) {
                        NormalTextComponent(value = stringResource(id = R.string.hello))
                        BoldTextComponent(value = stringResource(id = R.string.customerInfo))
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp))


                        ExposedDropdownMenuBox(
                            expanded =isExpanded ,
                            onExpandedChange ={
                            isExpanded=it
                            error.value=false
                        } ) {
                          DropDownTextField(labelValue = "Status", placeholder =statusPlaceholder , modifier = Modifier.menuAnchor(), data =status , expanded =isExpanded, error =error.value  )
                            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded=false}) {
                                Status.values().forEach { statusValue->
                                    DropdownMenuItem(
                                        text = { Text(text = statusValue.visibleName) },
                                        onClick = {
                                            status.value=statusValue.name
                                            isExpanded=false})

                                }
                            }
                        }


                        MultiLineInputTextField(labelValue = stringResource(id = R.string.description), data = comment, maxLines = 2)

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp))
                        ButtonComponent(
                            labelValue = stringResource(id =R.string.submit ), onClick = {
                                if (status.value.isEmpty()) {
                                    error.value =true
                                } else {
                                    val body= StatusHistoryDtoRequest(Status.valueOf(status.value), comment.value,price.value)
                                    viewModel.addStatusToService(viewModel.serviceId, body)
                                    navController.popBackStack()
                                }


                            }
                        )
                    }



    }
}
@Preview
@Composable
fun AddStatusPreview() {
    AddStatusScreen(navController = rememberNavController(), viewModel = AddStatusScreenViewModel(MockService(),1L))
}