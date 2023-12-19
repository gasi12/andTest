package com.example.andtest.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.InputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.components.PasswordField
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddServiceScreen(navController: NavController) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val pagerState = rememberPagerState(pageCount = {2})
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
                        BoldTextComponent(value = stringResource(id = R.string.createAccount))
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
                        // Your second screen content goes here
                        ButtonComponent(
                            labelValue = "Submit", onclick = {
                                // Handle your form submission here
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
    AddServiceScreen(navController = rememberNavController())
}

