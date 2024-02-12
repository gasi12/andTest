 package com.example.andtest.components

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.andtest.R
import com.example.andtest.api.dto.Customer
import com.example.andtest.api.dto.CustomerWithDevicesListDtoResponse
import com.example.andtest.api.dto.Device
import com.example.andtest.api.dto.ServiceRequest
import com.example.andtest.api.dto.ServiceRequestWithUserNameDtoResponse
import com.example.andtest.api.dto.StatusHistory
import com.example.andtest.api.dto.UserDto
import com.example.andtest.screens.RowWithValue
import com.example.andtest.screens.formatDateToReadableLocaleToString
import com.example.andtest.screens.formatDateToShortLocaleToString


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
fun ButtonComponent(labelValue: String, onClick: ()-> Unit,modifier: Modifier= Modifier){
    Button(
        onClick = onClick ,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .heightIn(45.dp)),
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
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.cancel))
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
            animation = tween(1400)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat()/1, size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeviceItem(
    deviceName: String,
    deviceType: String,
    deviceSerialNumber: String,
    onDeviceClick: () -> Unit
) {
    Box(
        modifier = Modifier
//            .padding(6.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(15.dp)
            )
            .combinedClickable(
                onLongClick = { },
                onClick = onDeviceClick
            ),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(R.string.device),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = deviceName,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp)
            )
            Text(
                text = stringResource(R.string.device_type),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = deviceType,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp)
            )
            Text(
                text = stringResource(R.string.device_serial_number),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = deviceSerialNumber,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeviceItemIcons(
    deviceName: String,
    deviceType: String,
    deviceSerialNumber: String,
    onDeviceClick: () -> Unit = {},
    primaryColor:Boolean
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(
                if (primaryColor) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(15.dp)
            )
            .combinedClickable(
                onLongClick = { },
                onClick = onDeviceClick
            ),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            SecondaryText(text = stringResource(R.string.deviceName), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)) {
                Icon(
                    painterResource(id = R.drawable.deviceinfo),
                    contentDescription = stringResource(R.string.deviceName),
                    modifier = Modifier.padding(end = 10.dp),
                    tint =   if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(text = deviceName,
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
            SecondaryText(text = stringResource(R.string.device_type), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                Icon(
                    painterResource(id = R.drawable.devicetype),
                    contentDescription = "Device type",
                    modifier = Modifier.padding(end = 10.dp),
                    tint =   if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(text = deviceType,
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
            SecondaryText(text = stringResource(R.string.serial_number), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                Icon(
                    painterResource(id = R.drawable.serialnumber),
                    contentDescription = "serial",
                    modifier = Modifier.padding(end = 10.dp),
                    tint =   if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(text = deviceSerialNumber,
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))

            }
        }
    }
}
@Composable
fun CustomerInfoIcons(firstName: String?, lastName: String?, phoneNumber: String?, primaryColor: Boolean) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(
                if (primaryColor) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(15.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            SecondaryText(text = stringResource(R.string.customer), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Customer",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                )

                if (firstName.toString()=="null"&&lastName.toString()=="null"){
                    Text(text = "", modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect())
                }else{
                    Text(
                        text = (firstName ?: "null") + " " + (lastName ?: "null"),
                        color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                    )   
                }
              
            }
            SecondaryText(text = stringResource(R.string.phoneNumber), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = stringResource(R.string.customer),
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = phoneNumber ?: "",
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.then(if (phoneNumber.isNullOrEmpty()||phoneNumber=="null") Modifier.shimmerEffect() else Modifier)
                )
            }
        }
    }
}

@Composable
fun CustomerDevicesIcons(
    customerWithDevices: CustomerWithDevicesListDtoResponse?,
    onDeviceClick: (Device) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CustomerInfoIcons(
            firstName = customerWithDevices?.firstName ?: "",
            lastName = customerWithDevices?.lastName ?: "",
            phoneNumber = customerWithDevices?.phoneNumber.toString(),
            primaryColor = true
        )
        LazyColumn {
            customerWithDevices?.devices?.let { devices ->
                items(devices) { device ->
                    DeviceItemIcons(
                        deviceName = device.deviceName,
                        deviceType = stringResource(id = device.deviceType.title),
                        deviceSerialNumber = device.deviceSerialNumber,
                        onDeviceClick = { onDeviceClick(device) },
                        primaryColor = false
                    )
                }
            }
        }
    }
}
@Composable
fun ServiceSummaryCardAlt(serviceRequest: ServiceRequestWithUserNameDtoResponse){
    Column(modifier= Modifier
        .padding(15.dp)
    ) {
        Row {
            RowWithValue(row =serviceRequest.customerFirstName.plus(" ").plus(serviceRequest.customerLastName) , description = stringResource(
                id = R.string.customer
            ))
            Spacer(Modifier.weight(1f))
            Text(
                text = formatDateToShortLocaleToString(serviceRequest.startDate),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        RowWithValue(row =serviceRequest.deviceName , description = stringResource(id = R.string.deviceName))
        RowWithValue(row =   stringResource(id = serviceRequest.lastStatus.title),  description = "Status")
        RowWithValue(row = serviceRequest.description, description = stringResource(id = R.string.description),overflow = true )
    }
}
@Composable
fun CustomerCardAlt(customer: Customer){
    Column(modifier= Modifier
        .padding(15.dp)
        .fillMaxWidth()
    ) {
        RowWithValue(row =customer.firstName.plus(" ").plus(customer.lastName) , description = stringResource(R.string.customer))
        RowWithValue(row =customer.phoneNumber.toString() , description = stringResource(R.string.phoneNumber))
    }
}
@Composable
fun DeviceCardAlt(device: Device){
    Column(modifier= Modifier
        .padding(15.dp)
        .fillMaxWidth()
    ) {
        RowWithValue(row =device.deviceName , description = stringResource(id = R.string.description))
        RowWithValue(row =stringResource(id = device.deviceType.title) , description = stringResource(id = R.string.name))
        RowWithValue(row =device.deviceSerialNumber , description = stringResource(id = R.string.serial_number))
    }
}
@Composable
fun UserCard(user: UserDto){
    Column(modifier= Modifier
        .padding(15.dp)
        .fillMaxWidth()
    ) {
        RowWithValue(row =user.firstName.plus(" ").plus(user.lastName) , description = stringResource(id = R.string.user))
        RowWithValue(row =user.email , description = "Email")
        RowWithValue(row =if(user.role=="CUSTOMER"){
            stringResource(id = R.string.waitingForRegister)}else user.role , description = stringResource(id = R.string.role ))
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServiceRequestIcons(
    serviceRequest: ServiceRequest?,

    onServiceClick: () -> Unit = {},
    primaryColor: Boolean
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(
                if (primaryColor) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(15.dp)
            )
            .combinedClickable(
                onLongClick = { },
                onClick = onServiceClick
            ),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            SecondaryText(text = stringResource(id = R.string.description), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)) {
                // Placeholder for description icon
                Icon(
                    painterResource(id = R.drawable.description), // Replace with actual icon resource
                    contentDescription = stringResource(id = R.string.description),
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(text = serviceRequest?.description?:"",
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp),
                    overflow =  TextOverflow.Ellipsis,
//                    maxLines = 3

                )
            }
            SecondaryText( stringResource(id = R.string.lastStatus), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                // Placeholder for last status icon
                Icon(
                    painterResource(id = R.drawable.info), // Replace with actual icon resource
                    contentDescription = stringResource(id = R.string.lastStatus),
                    modifier = Modifier.padding(end = 10.dp),
                    tint =if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                 Text(text =  stringResource(id = serviceRequest?.lastStatus?.title?:0),
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
            SecondaryText(text = stringResource(id = R.string.endDate), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                // Placeholder for end date icon
                Icon(
                    painterResource(id = R.drawable.end_date), // Replace with actual icon resource
                    contentDescription = "End Date",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(text = if(serviceRequest?.endDate!=null)formatDateToReadableLocaleToString(serviceRequest.endDate) else stringResource(id = R.string.noEndDate),
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
            SecondaryText(text = stringResource(id = R.string.startDate), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                // Placeholder for start date icon
                Icon(
                    painterResource(id = R.drawable.start_date), // Replace with actual icon resource
                    contentDescription = "Start Date",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(text = if(serviceRequest?.startDate!=null)formatDateToReadableLocaleToString(serviceRequest.startDate) else "???",
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
            SecondaryText(text = stringResource(id = R.string.price), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                // Placeholder for price icon
                Icon(
                    painterResource(id = R.drawable.price), // Replace with actual icon resource
                    contentDescription = "Price",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(text = if(serviceRequest?.price==0L)stringResource(id = R.string.noPrice) else serviceRequest?.price.toString(),
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatusHistoryIcons(
    statusHistory: StatusHistory?,

    onServiceClick: () -> Unit = {},
    primaryColor: Boolean
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(
                if (primaryColor) MaterialTheme.colorScheme.tertiaryContainer
                else MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(15.dp)
            )
            .combinedClickable(
                onLongClick = { },
                onClick = onServiceClick
            ),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            SecondaryText(text = stringResource(id = R.string.status), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)) {
                // Placeholder for description icon
                Icon(
                    painterResource(id = R.drawable.description), // Replace with actual icon resource
                    contentDescription = "Status",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )
                val status = statusHistory?.status // Replace with the actual status you want to display

                Text(text =  stringResource(id = status?.title?:0),
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp),
                    overflow =  TextOverflow.Ellipsis,
                    maxLines = 3

                )
            }
            SecondaryText(text = stringResource(id = R.string.comment), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                // Placeholder for last status icon
                Icon(
                    painterResource(id = R.drawable.info), // Replace with actual icon resource
                    contentDescription = "Comment",
                    modifier = Modifier.padding(end = 10.dp),
                    tint =if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(text = statusHistory?.comment?:"",
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
            SecondaryText(text = stringResource(id = R.string.date), modifier = Modifier.padding(horizontal = 35.dp))
            Row(Modifier.padding(vertical = 3.dp)){
                // Placeholder for end date icon
                Icon(
                    painterResource(id = R.drawable.end_date), // Replace with actual icon resource
                    contentDescription = "End Date",
                    modifier = Modifier.padding(end = 10.dp),
                    tint = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(text = formatDateToReadableLocaleToString(statusHistory?.time),
                    color = if (primaryColor) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(0.dp))
            }
        }
    }
}
@Composable
fun SecondaryText(text:String, modifier: Modifier= Modifier){
    Text(style = TextStyle(
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        ),
    ),
        text = text,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(bottom = 0.dp)
            .then(modifier),
        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4F)
    )
}