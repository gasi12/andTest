package com.example.andtest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.api.service.MockService
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.InputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.components.PasswordField
import com.example.andtest.viewModels.LoginScreenViewModel

@Composable

fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel) {

    val navigateToHome by viewModel.navigateToHome
    val loginError by viewModel.loginError.observeAsState("")
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

//    val context = LocalContext.current


    Surface(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {

            Column (modifier= Modifier.fillMaxWidth()){
                NormalTextComponent(value = stringResource(id = R.string.hello))
                BoldTextComponent(value = stringResource(id = R.string.login))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp))
                InputTextField(labelValue = stringResource(id = R.string.email), data = email)
                PasswordField(labelValue = stringResource(id = R.string.password),data = password)
                Text(textAlign = TextAlign.Center, color = Color.Red,text = loginError)
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp))
                ButtonComponent(
                    labelValue = stringResource(id = R.string.login), onclick = {
                        viewModel.login(email.value,password.value)


                    }
                )
                LaunchedEffect(navigateToHome) {
                    if (navigateToHome) {
                        navController.navigate("Home") {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            Column (modifier= Modifier
                .fillMaxWidth()
                .background(Color.Blue)){
                //spacer to divide everyting into 3
            }
            Column (modifier= Modifier.fillMaxWidth()){
                Divider(modifier = Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)


                Column {
                    Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                        Text(text = "You don't have an account? ")
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("Register here")
                            }
                        }
                        ClickableText(modifier = Modifier,text = annotatedString, onClick ={ navController.navigate("Register")})
                    }
                }


            }


        }

    }
}

@Preview
@Composable
fun DefaultPreviewLoginScreen() {

    LoginScreen(navController = rememberNavController(), viewModel = LoginScreenViewModel(
        MockService()
    )
    )
}