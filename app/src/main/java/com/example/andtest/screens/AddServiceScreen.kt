package com.example.andtest.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.api.service.MockService
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.InputTextField
import com.example.andtest.components.LastInputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.AddServiceScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddServiceScreen(navController: NavController,viewModel: AddServiceScreenViewModel) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val pagerState = rememberPagerState(pageCount = {2})
    val description = remember { mutableStateOf("") }
    val deviceName = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()




    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
            when (page) {
                0 -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        NormalTextComponent(value = stringResource(id = R.string.hello))
                        BoldTextComponent(value = stringResource(id = R.string.customerInfo))
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp))
                        InputTextField(labelValue = stringResource(id = R.string.firstName), data = firstName)
                        InputTextField(labelValue = stringResource(id = R.string.lastName), data = lastName)
                        InputTextField(labelValue = stringResource(id = R.string.phoneNumber), data = phoneNumber)
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp))
                        ButtonComponent(
                            labelValue = "Next", onclick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)


                                }
                            }
                        )
                    }
                }
                1 -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        NormalTextComponent(value = stringResource(id = R.string.hello))
                        BoldTextComponent(value = stringResource(id = R.string.serviceInfo))
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp))

                        InputTextField(labelValue = stringResource(id = R.string.description), data = description, maxLines = 4)
                        InputTextField(labelValue = stringResource(id = R.string.deviceName), data = deviceName)
                        LastInputTextField(labelValue = stringResource(id = R.string.price), data = price)
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp))
                        ButtonComponent(
                            labelValue = stringResource(id = R.string.submit) , onclick = {
                               navController.navigate(Screen.HOME.name)
                            }
                        )
                    }
                }
            }
        }

    }
}


//@PreviewScreenSizes
@Preview
@Composable
fun AddServiceScreenPreview() {
    AddServiceScreen(navController = rememberNavController(), viewModel = AddServiceScreenViewModel(MockService()))
}

