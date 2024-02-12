package com.example.andtest.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    val passwordError= remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
//    val context = LocalContext.current

    val fields = listOf(
        email to emailError,
        password to passwordError
    )
    fields.forEach { (field, error) ->
        LaunchedEffect(field.value) {
            if (field.value.isNotEmpty()) {
                error.value = false
            }
        }
    }
    Surface(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {

            Column (modifier= Modifier.fillMaxWidth()){
                Spacer(modifier = Modifier.height(20.dp))
//                NormalTextComponent(value = stringResource(id = R.string.hello))
                BoldTextComponent(value = stringResource(id = R.string.login))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp))
                InputTextField(labelValue = stringResource(id = R.string.email), data = email, error = emailError.value)
                PasswordField(labelValue = stringResource(id = R.string.password),data = password,error = passwordError.value

                )
                if(navigateToHome==false){
                    Text(textAlign = TextAlign.Center, color = Color.Red,text = "Wrong username or password!")
                }

//                Spacer(modifier = Modifier
//                    .fillMaxWidth()
//                    .height(80.dp))



                if(isLoading.value){
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }

                }

                LaunchedEffect(navigateToHome) {
                    when (navigateToHome) {
                        true -> {

                            navController.navigate("Home") {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
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
//            Spacer(modifier = Modifier.weight(0.3f))
            Column(Modifier.imePadding().padding(horizontal = 20.dp)) {
                ButtonComponent(
                    labelValue = stringResource(id = R.string.login), onClick = {
                        var allFieldsNotEmpty = true
                        fields.forEach { (field, error) ->
                            if (field.value.isEmpty()) {
                                error.value = true
                                allFieldsNotEmpty = false
                            }
                        }
                        if (allFieldsNotEmpty) {
                            isLoading.value = true
                            viewModel.login(email.value, password.value)
                        }
                    }
                )
            }
            Column (modifier= Modifier
                .fillMaxWidth()
                .background(Color.Blue)){
                //spacer to divide everyting into 3
            }
            Column (modifier= Modifier.fillMaxWidth()){
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )


                Column {
                    Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                        Text(text = stringResource(R.string.no_account))
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                                append(" ")
                                append(stringResource(R.string.register_here))
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