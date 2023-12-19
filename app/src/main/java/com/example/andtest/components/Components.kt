package com.example.andtest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.andtest.R


@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}
    @Composable
    fun BoldTextComponent(value:String){
        Text(
            text = value,
            modifier= Modifier
                .fillMaxWidth()
                .heightIn(),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            textAlign = TextAlign.Center
        )
    }
    @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun InputTextField(labelValue:String,data : MutableState<String>){

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(labelValue) },
                value = data.value,

                colors = TextFieldDefaults.outlinedTextFieldColors(

                    focusedBorderColor = Color(0xff92a3fd),
                    focusedLabelColor = Color(0xff92a3fd),
                    cursorColor = Color(0xff92a3fd),
                    containerColor = Color.Cyan
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                maxLines = 1,

                onValueChange = { data.value = it

                }
            )
        }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(labelValue:String,data:MutableState<String>){


    val localFocusManager = LocalFocusManager.current
    val isPasswordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(labelValue) },
        value = data.value,

        colors = TextFieldDefaults.outlinedTextFieldColors(

            focusedBorderColor = Color(0xff92a3fd),
            focusedLabelColor = Color(0xff92a3fd),
            cursorColor = Color(0xff92a3fd),
            containerColor = Color.Cyan,

        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        visualTransformation = if(isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { data.value = it },
        trailingIcon = {

            val iconImage = if(isPasswordVisible.value)
                R.drawable.baseline_visibility_24
            else
                R.drawable.baseline_visibility_off_24


            val description = if(isPasswordVisible.value)
                R.string.passwordVisible
            else
                R.string.passwordHidden
            IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                Icon(
                    painter = painterResource(iconImage),
                    contentDescription = description.toString(),
                    tint = Color.Red
                )
            }
        }

    )
}

@Composable
fun ButtonComponent(labelValue: String,onclick: ()-> Unit){
    Button(
        onClick = onclick ,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp),
    contentPadding= PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    )

    {
Box(modifier = Modifier
    .fillMaxWidth()
    .heightIn(45.dp)
    .background(
        brush = Brush.horizontalGradient(listOf(Color.Blue, Color.DarkGray)),
        shape = RoundedCornerShape(50.dp)
    ), contentAlignment = Alignment.Center
)
{
Text(text = labelValue,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold)
}

    }
}


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(

        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}