package com.example.andtest.screens

import android.util.Log
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.andtest.viewModels.SignupScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController,viewModel: SignupScreenViewModel) {
    val firstName = remember{mutableStateOf("")}
    val lastName = remember{mutableStateOf("")}
    val email = remember{mutableStateOf("")}
    val password = remember{mutableStateOf("")}
    val snackState = remember { SnackbarHostState() }
    val scope  = rememberCoroutineScope()
val navigateToHome by viewModel.navigateToHome
    val notInvited by viewModel.notInvited
    LaunchedEffect(navigateToHome) {
        when (navigateToHome) {
            true -> {

                navController.navigate("Home") {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
               // isLoading.value=false
            }
            false ->
            {

                //isLoading.value=false
            }
            null -> {

            }
        }
    }
    LaunchedEffect(notInvited){
        when(notInvited){
            true ->{
                scope.launch {
                    Log.i("A","SNAC")
                    snackState.showSnackbar("You have not been invited, please contact the administrator")
                    viewModel.notInvited.value=false
                }
            }
            false ->{

            }
            null -> {

            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween) {

            Column (modifier=Modifier.fillMaxWidth()){
                NormalTextComponent(value = stringResource(id = R.string.hello))
                BoldTextComponent(value = stringResource(id = R.string.createAccount))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp))
                InputTextField(labelValue = stringResource(id = R.string.firstName),data=firstName)
                InputTextField(labelValue = stringResource(id = R.string.lastName),data=lastName)
                InputTextField(labelValue = stringResource(id = R.string.email),data=email)
                PasswordField(labelValue = stringResource(id = R.string.password),data = password)
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp))
                ButtonComponent(
                    labelValue = "BUTTON", onClick = {
                        viewModel.register(firstName.value,lastName.value,email.value,password.value)
                    }
                )
            }
            SnackbarHost(hostState = snackState)
            Column (modifier= Modifier
                .fillMaxWidth()
                .background(Color.Blue)){
                //spacer to divide everyting into 3
            }
            Column (modifier= Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )


                Column {
                    Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                        Text(text = "Already have an account? ")
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("Sign in")
                            }

                        }
                        ClickableText(modifier = Modifier,text = annotatedString, onClick ={ navController.navigate("Login")})
                    }
                }

            }
        }

    }
}
