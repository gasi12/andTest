package com.example.andtest.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.R
import com.example.andtest.SecurePreferences
import com.example.andtest.api.downloader.AndroidDownloader
import com.example.andtest.api.service.MockService
import com.example.andtest.api.service.ServiceInterface
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.CustomerSummaryScreenFactory
import com.example.andtest.viewModels.CustomerSummaryScreenViewModel
import com.example.andtest.viewModels.DeviceSummaryScreenFactory
import com.example.andtest.viewModels.DeviceSummaryScreenViewModel
import com.example.andtest.viewModels.ServicesSummaryScreenFactory
import com.example.andtest.viewModels.ServicesSummaryScreenViewModel
import com.example.andtest.viewModels.SharedViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (authService:ServiceInterface,navController: NavController,sharedViewModel: SharedViewModel){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
        val securePreferences = SecurePreferences
            .getInstance(context)
    val firstName = securePreferences.getAnything("firstName")
    val lastName = securePreferences.getAnything("lastName")
    val username = securePreferences.getAnything("username")
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by sharedViewModel.selectedItem
        var topBarQuery by sharedViewModel.topBarQuery
        val downloader = AndroidDownloader(context)

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = GmsBarcodeScanning.getClient(context, options)




        LaunchedEffect(selectedItem){
            topBarQuery = ""
            localFocusManager.clearFocus()
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(300.dp)) {

                    Column(
                            modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .fillMaxWidth()
                                .padding(20.dp)
                                .height(150.dp)) {
                            Box(modifier = Modifier
                                .padding(4.dp)
                                .border(1.dp, Color.White, shape = CircleShape)
                                .size(70.dp)
                                .aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                                contentAlignment = Alignment.Center){
                                Text(text = firstName.substring(0,1).uppercase(),
                                    style = TextStyle(
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Normal),
                                    color = Color.White
                                )

                            }
                            Spacer(modifier = Modifier.weight(1f, true))
                            Text(text = "$firstName $lastName",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                fontWeight = FontWeight.Bold), modifier = Modifier.padding(bottom = 10.dp))

                            Text(text = username)
                        }
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp))
                        // Your drawer content here
                        NavigationDrawerItem(
                            icon = { Icon(painter = painterResource(id = R.drawable.tools), contentDescription = null) },
                            label = { Text(stringResource(id = R.string.servicesScreen)) },
                            selected = selectedItem == Screen.SERVICESUMMARY.name,
                            onClick = {

                                sharedViewModel.selectedItem.value = Screen.SERVICESUMMARY.name
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)

                        )
                        NavigationDrawerItem(
                            icon = { Icon(painterResource(id = R.drawable.person), contentDescription = null) },
                            label = { Text(stringResource(id = R.string.customerScreen)) },
                            selected = selectedItem == Screen.CUSTOMERSUMMARY.name,
                            onClick = {

                                sharedViewModel.selectedItem.value = Screen.CUSTOMERSUMMARY.name
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
//                                    colors = NavigationDrawerItemDefaults.colors(
//                                    selectedTextColor = MaterialTheme.colorScheme.primary, // Change to your desired color
//                            selectedIconColor = MaterialTheme.colorScheme.primary,
//                                        selectedContainerColor = MaterialTheme.colorScheme.onPrimary
//
//                        )
                        )
                        NavigationDrawerItem(
                            icon = { Icon(painterResource(id = R.drawable.devices), contentDescription = null) },
                            label = { Text(stringResource(id = R.string.deviceSummary)) },
                            selected = selectedItem == Screen.DEVICESUMMARY.name,
                            onClick = {

                                sharedViewModel.selectedItem.value = Screen.DEVICESUMMARY.name
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                        if(securePreferences.getAnything("role") == "ADMIN"){
                            NavigationDrawerItem(
                                icon = { Icon(painterResource(id = R.drawable.baseline_visibility_off_24), contentDescription = null) },
                                label = { Text(stringResource(id = R.string.admin)) },
                                selected = selectedItem == Screen.ADMINSCREEN.name,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()

                                    }
                                    navController.navigate(Screen.ADMINSCREEN.name)


//                                    sharedViewModel.selectedItem.value = Screen.ADMINSCREEN.name
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }


                        Spacer(modifier = Modifier.weight(1f, true))

                            
                            NavigationDrawerItem(
                                icon = { Icon(painterResource(id = R.drawable.logout), contentDescription = "Logout") },
                                label = { Text(stringResource(id = R.string.logout)) },
                                selected = false,
                                onClick = {
                                   
                                    securePreferences.clearTokens()
                                    navController.navigate(Screen.LOGIN.name) {
                                        popUpTo(navController.graph.id) {
                                            inclusive = true
                                        }
                                    }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )

                    }

                }
                }
        ){
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        actions = {
                            Box( modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable(
                                    onClick = {
                                        scanner
                                            .startScan()
                                            .addOnSuccessListener { barcode ->
                                                val rawValue: String? = barcode.rawValue
                                                if (rawValue != null) {
                                                    if (rawValue.startsWith("/services/service/")) {
                                                        Log.i("Scanner", barcode.rawValue ?: "nic")
                                                        val id = rawValue?.substringAfterLast("/")
                                                        Log.i("Scanner", id ?: "null")
                                                        navController.navigate("${Screen.SERVICEDETAILS.name}/${id}")
                                                    }

                                                }
                                            }
                                            .addOnCanceledListener {
                                                // Task canceled
                                            }
                                            .addOnFailureListener { e ->
                                                Log.i("QRScanner exception", e.toString())
                                            }

                                    }
                                )) {
                                Icon(painter = painterResource(R.drawable.qrscanner),"Scan qr")
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(15.dp)
                            .clip(shape = RoundedCornerShape(30.dp)),
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    modifier = Modifier
//                                        .width(220.dp)
                                     ,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent
                                    ),
                                    label = { Text(stringResource(id = R.string.search)) },
                                    leadingIcon = {Icon(Icons.Default.Search,"SEARCH")},
                                    value = topBarQuery,
                                    onValueChange ={ topBarQuery = it },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search,keyboardType = KeyboardType.Text),
                                    keyboardActions = KeyboardActions(
                                        onSearch = { localFocusManager.clearFocus() }
                                    ))


                            }
                                },
                        colors = topAppBarColors(
                          containerColor = MaterialTheme.colorScheme.primaryContainer,
                            scrolledContainerColor = MaterialTheme.colorScheme.surface

                         ),
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    localFocusManager.clearFocus()
                                    drawerState.open()

                                } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }

            ){
                Box(modifier = Modifier.padding(it)){
                    when (selectedItem) {
                        Screen.SERVICESUMMARY.name -> {
                            val viewModel: ServicesSummaryScreenViewModel =
                                viewModel(factory = ServicesSummaryScreenFactory(authService,sharedViewModel))
                            ServicesSummaryScreen(viewModel, navController)
                        }
                        Screen.CUSTOMERSUMMARY.name ->{
                            val viewModel: CustomerSummaryScreenViewModel =
                                viewModel(factory = CustomerSummaryScreenFactory(authService,sharedViewModel))
                            CustomerSummaryScreen(viewModel,navController)
                        }
                        Screen.DEVICESUMMARY.name ->{
                            val viewModel: DeviceSummaryScreenViewModel =
                                viewModel(factory = DeviceSummaryScreenFactory(authService,sharedViewModel))
                            DeviceSummaryScreen(viewModel,navController)
                        }

                        }
                }

            }
        }
    }
}

//@PreviewScreenSizes
@Composable
@Preview
fun mainScreenPreview(){
    MainScreen(MockService(), rememberNavController(), viewModel())
}