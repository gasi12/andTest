package com.example.andtest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.andtest.MainActivity
import com.example.andtest.components.BoldTextComponent
import com.example.andtest.components.NormalTextComponent
import com.example.andtest.navigation.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (navController: NavController){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember { mutableStateOf(Screen.EX.name) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                    // Your drawer content here
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        label = { Text(Screen.EX.name) },
                        selected = selectedItem == Screen.EX.name,
                        onClick = {
                            selectedItem = Screen.EX.name
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text(Screen.EX2.name) },
                        selected = selectedItem == Screen.EX2.name,
                        onClick = {
                            selectedItem = Screen.EX2.name
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
                            Screen.EX.name -> ExampleScreen()
                            Screen.EX2.name -> ExampleScreen2()
                            else -> Text("Screen not found")
                        }
                    }
                }
            )
        }
    }
}


@Composable
@Preview
fun mainScreenPreview(){
    MainScreen(navController = rememberNavController())
}
//Scaffold(
//topBar = {
//    TopAppBar(
//        title = {
//            Text(text = "Top App Bar")
//        },
//        navigationIcon = {
//            IconButton(onClick = {}) {
//                Icon(Icons.Filled.Settings, "backIcon")
//            }
//        },
//
//        )
//}, content = {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xff8d6e63)),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Content of the page",
//            fontSize = 30.sp,
//            color = Color.White
//        )
//    }
//
//})
