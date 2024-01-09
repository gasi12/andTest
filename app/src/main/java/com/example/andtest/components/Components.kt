package com.example.andtest.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
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

        @Composable
        fun InputTextField(labelValue:String,data : MutableState<String>, modifier: Modifier = Modifier,error: Boolean =false,readOnly:Boolean=false){

             OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
                    ,
                label = { Text(labelValue) },
                value = data.value,

                isError = error,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine =true,
                 trailingIcon = {
                     if(error){ Icon(Icons.Filled.Warning,"ADAS" )

                     }
                 },
//                leadingIcon = { Icon(Icons.Filled.,"ADAS" )},
                maxLines = 1,
                readOnly =readOnly ,
                enabled = !readOnly,
                onValueChange = {
                    data.value = it
                }
             )
        }
@Composable
fun InputNumberField(labelValue:String,data : MutableState<String>, modifier: Modifier = Modifier,error: Boolean =false,readOnly:Boolean=false){

    val pattern = remember { Regex("^\\d+\$") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
        ,
        label = { Text(labelValue) },
        value = data.value,

        isError = error,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next,keyboardType = KeyboardType.Number),
        singleLine =true,
        trailingIcon = {
            if(error){ Icon(Icons.Filled.Warning,"ADAS" )

            }
        },
//                leadingIcon = { Icon(Icons.Filled.,"ADAS" )},
        maxLines = 1,
        readOnly =readOnly ,
        enabled = !readOnly,

        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) {
                data.value = it
            }
        }
    )
}
@Composable
fun MultiLineInputTextField(labelValue:String,data : MutableState<String>, modifier: Modifier = Modifier,maxLines: Int ,error: Boolean =false,readOnly:Boolean=false){
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        label = { Text(labelValue) },
        value = data.value,
        isError = error,
        trailingIcon = {
            if(error){ Icon(Icons.Filled.Warning,"field empty" )

            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = false,
//                leadingIcon = { Icon(Icons.Filled.,"ADAS" )},
        maxLines = maxLines,
        readOnly =readOnly ,
        enabled = !readOnly,
        onValueChange = {
            if (it.contains("\n")) {
                localFocusManager.clearFocus()
                keyboardController?.hide() //todo zadrutowane zeby chowac klawiature kiedy jest multiline
            }else
                data.value = it
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(
    labelValue:String,
    placeholder: String,
    data: MutableState<String>, modifier: Modifier = Modifier,
    expanded: Boolean,
    error:Boolean
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        label = { Text(labelValue) },
        value = placeholder,
        placeholder = { Text(text = placeholder)},
        isError = error,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
        readOnly = true,
        onValueChange = { data.value = it
        }
    )
}
@Composable
fun LastInputTextField(
    labelValue: String,
    data: MutableState<String>,
    modifier: Modifier = Modifier,
    onEnterPressed: (() -> Unit)? = null,
    error: Boolean = false,
    readOnly: Boolean = false
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        label = { Text(labelValue) },
        value = data.value,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onEnterPressed?.invoke()
            keyboardController?.hide()
        }),
        isError = error,
        readOnly =readOnly,
        enabled = !readOnly,
        singleLine = true,
        trailingIcon = {
                       if(error){ Icon(Icons.Filled.Warning,"ADAS" )
                           
                       }
        },

        maxLines = 1,
        onValueChange = { data.value = it

        }
    )
}
@Composable
fun LastInputNumberField(
    labelValue: String,
    data: MutableState<String>,
    modifier: Modifier = Modifier,
    onEnterPressed: (() -> Unit)? = null,
    error: Boolean = false,
    readOnly: Boolean = false
) {
    val pattern = remember { Regex("^\\d+\$") }
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        label = { Text(labelValue) },
        value = data.value,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onNext = {
            onEnterPressed?.invoke()
            keyboardController?.hide()
        }),
        isError = error,
        readOnly =readOnly,
        enabled = !readOnly,
        singleLine = true,
        trailingIcon = {
            if(error){ Icon(Icons.Filled.Warning,"ADAS" )

            }
        },

        maxLines = 1,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) {
                data.value = it
            }
        }

    )
}
@Composable
fun PasswordField(labelValue:String,data:MutableState<String>,error: Boolean=false, onEnterPressed: (() -> Unit)? = null){


    val localFocusManager = LocalFocusManager.current
    val isPasswordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(labelValue) },
        value = data.value,

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone  = {
            onEnterPressed?.invoke()
            localFocusManager.clearFocus()
        }),
        visualTransformation = if(isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { data.value = it },
        isError = error,
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
//                    tint = Color.Red
                )
            }
        }

    )
}

@Composable
fun ButtonComponent(labelValue: String, onClick: ()-> Unit){
    Button(
        onClick = onClick ,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp),
    contentPadding= PaddingValues(),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor =MaterialTheme.colorScheme.onTertiary

        )
    )

    {
Text(text = labelValue, color = MaterialTheme.colorScheme.onPrimaryContainer)

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
    AlertDialog(modifier = Modifier.fillMaxWidth(),

        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle, textAlign = TextAlign.Center)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center)
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
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}