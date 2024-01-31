package com.example.andtest.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.andtest.R
import com.example.andtest.api.dto.ServiceRequestEditor
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.ButtonComponent
import com.example.andtest.components.InputNumberField
import com.example.andtest.components.LastInputTextField
import com.example.andtest.components.MultiLineInputTextField
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.viewModels.EditServiceScreenViewModel
import com.example.andtest.viewModels.InviteUserViewModel
import kotlinx.coroutines.launch

@Composable
fun InviteUser(navController: NavController, viewModel: InviteUserViewModel) {
    val email = remember { mutableStateOf("") }
    val isSentSuccessfully by viewModel.isSentSuccessfully
    val isLoading = remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val scope  = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {



        Column(modifier = Modifier.fillMaxWidth()) {
            NormalTextComponent(value = stringResource(id = R.string.hello))
            BoldTextComponent(value = stringResource(id = R.string.editService))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp))



            LastInputTextField(labelValue = "User Email", data = email)
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp))
            ButtonComponent(
                labelValue = stringResource(id = R.string.submit ), onClick = {
              viewModel.inviteUser(email.value)
                }
            )
            Spacer(modifier = Modifier.weight(1f, true))
            SnackbarHost(hostState = snackState)


            if(isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            }
        }
        LaunchedEffect(isSentSuccessfully) {
            Log.i("issentsuccesflly",isSentSuccessfully.toString())
            when (isSentSuccessfully) {
                true -> {

                    navController.navigateUp()
                    isLoading.value=false
                }
                false ->
                {
                    scope.launch {
                        localFocusManager.clearFocus()
                        keyboardController?.hide()
                        snackState.showSnackbar("CHUJ")
                    }

                    isLoading.value=false
                    viewModel.resetState()
                }
                null -> {
                }
            }
        }


    }
}


