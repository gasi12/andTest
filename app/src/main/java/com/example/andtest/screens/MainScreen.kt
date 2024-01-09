package com.example.andtest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.SecurePreferences
import com.example.andtest.api.service.MockService
import com.example.andtest.api.service.ServiceInterface
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.CustomerSummaryScreenFactory
import com.example.andtest.viewModels.CustomerSummaryScreenViewModel
import com.example.andtest.viewModels.ServicesSummaryScreenFactory
import com.example.andtest.viewModels.ServicesSummaryScreenViewModel
import com.example.andtest.viewModels.SharedViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (authService:ServiceInterface,navController: NavController,sharedViewModel: SharedViewModel){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val localFocusManager = LocalFocusManager.current
        val securePreferences = SecurePreferences
            .getInstance(LocalContext.current)
    val firstname = securePreferences.getAnything("firstName")
    val lastname = securePreferences.getAnything("lastName")
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by sharedViewModel.selectedItem
        var topBarQuery by sharedViewModel.topBarQuery

        LaunchedEffect(selectedItem){
            topBarQuery = ""
            localFocusManager.clearFocus()
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                    Box{
                        Text(text = "Hello $firstname $lastname")
                    }
                    // Your drawer content here
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        label = { Text(Screen.SUMMARY.name) },
                        selected = selectedItem == Screen.SUMMARY.name,
                        onClick = {

                            sharedViewModel.selectedItem.value = Screen.SUMMARY.name
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text(Screen.CUSTOMER.name) },
                        selected = selectedItem == Screen.CUSTOMER.name,
                        onClick = {

                            sharedViewModel.selectedItem.value = Screen.CUSTOMER.name
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        ){
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        scrollBehavior = scrollBehavior,
                        modifier = Modifier.background(Color.Transparent).padding(15.dp)
                            .clip(shape = RoundedCornerShape(30.dp)),
                        title = {
                            TextField(
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent
                                ),
                                label = { Text("Search") },
                               leadingIcon = {Icon(Icons.Default.Search,"SEARCH")},
                                value = topBarQuery,
                                onValueChange ={ topBarQuery = it },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search,keyboardType = KeyboardType.Text),
                                keyboardActions = KeyboardActions(
                                    onSearch = { localFocusManager.clearFocus() }
                                ))
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
                        Screen.SUMMARY.name -> {
                            val viewModel: ServicesSummaryScreenViewModel =
                                viewModel(factory = ServicesSummaryScreenFactory(authService,sharedViewModel))
                            ServicesSummaryScreen(viewModel, navController)
                        }
                        Screen.CUSTOMER.name ->{
                            val viewModel: CustomerSummaryScreenViewModel =
                                viewModel(factory = CustomerSummaryScreenFactory(authService,sharedViewModel))
                            CustomerSummaryScreen(viewModel,navController)
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