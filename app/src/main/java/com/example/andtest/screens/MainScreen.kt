package com.example.andtest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.SecurePreferences
import com.example.andtest.api.service.MockService
import com.example.andtest.api.service.ServiceInterface
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.ServicesSummaryScreenFactory
import com.example.andtest.viewModels.ServicesSummaryScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (navController: NavController,authService: ServiceInterface){


        val securePreferences = SecurePreferences
            .getInstance(LocalContext.current)
    val servicesSummaryScreenViewModel: ServicesSummaryScreenViewModel =
        viewModel(factory = ServicesSummaryScreenFactory(authService))

    val firstname = securePreferences.getAnything("firstName")
    val lastname = securePreferences.getAnything("lastName")
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember { mutableStateOf(Screen.SUMMARY.name) }
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
                            selectedItem = Screen.SUMMARY.name
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text(Screen.DETAILS.name) },
                        selected = selectedItem == Screen.DETAILS.name,
                        onClick = {
                            selectedItem = Screen.DETAILS.name
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        ){
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.background(Color.Yellow),
                        title = { Text("Navigation Drawer Example") },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.LightGray),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xff8d6e63))
                            .padding(top = 64.dp)
                    ) {
                        when (selectedItem) {
                            Screen.SUMMARY.name -> ServicesSummaryScreen(servicesSummaryScreenViewModel,navController)
                          //  Screen.DETAILS.name -> ServiceDetailsScreen(navController)
                            else -> Text("Screen not found")
                        }
                    }
                }
            )
        }
    }
}

//@PreviewScreenSizes
@Composable
@Preview
fun mainScreenPreview(){
    MainScreen(navController = rememberNavController(), authService = MockService())
}