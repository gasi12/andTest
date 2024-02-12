package com.example.andtest.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.andtest.R
import com.example.andtest.components.DeviceCardAlt
import com.example.andtest.components.UserCard
import com.example.andtest.navigation.Screen
import com.example.andtest.viewModels.AdminScreenViewModel
import com.example.andtest.viewModels.DeviceSummaryScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Route

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun AdminScreen(viewModel: AdminScreenViewModel, navController: NavController){
    Button(onClick ={ navController.navigate(Screen.REGISTER.name)}) {
        Text(text = "devicescreen")
    }
    var isPromoted by viewModel.isPromoted
    var topBarQuery by viewModel.sharedViewModel.topBarQuery
    val userList by viewModel.sharedViewModel.users.observeAsState(initial = emptyList())
    val isLastPageEmpty by viewModel.sharedViewModel.isLastPageEmptyUsers
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val itemsToDisplay = 100
    val initialDataLoaded by viewModel.isDataPresent
    val endOfListReached by derivedStateOf {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
    }
    val filteredDevices = userList.filter { it.email.toString().startsWith(topBarQuery)  } //nieuzywane, ale nie chce mi sie zmieniac LaunchedEffect(endOfListReached)  bo dziala

    LaunchedEffect(endOfListReached) {
        if (endOfListReached && initialDataLoaded == true && userList.isNotEmpty()) {
            Log.i("whos calling page?","Launcheffect")
            Log.i("filtered sr",filteredDevices.toString())
            coroutineScope.launch {
                if (topBarQuery.isEmpty() && filteredDevices.size < itemsToDisplay) {
                    viewModel.sharedViewModel.getAllDevices(40)
                } else if (topBarQuery.isNotEmpty() && !isLastPageEmpty!!) {
                    var shouldContinue = true
                    while(shouldContinue){
                        delay(400)
                        viewModel.sharedViewModel.getAllDevices(40)
                        if (filteredDevices.isNotEmpty()) {
                            shouldContinue = false
                        }
                    }
                }
            }
        }
    }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(500)
        viewModel.sharedViewModel.refreshUsers()
        refreshing = false
        Log.i("device summary", "refreshing")

    }



    LaunchedEffect(isPromoted){
        if (isPromoted==true){
            refresh()
            isPromoted=false
        }
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)

    Surface(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(Color.Yellow),
                    title = { Text(stringResource(R.string.admin))},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp()}) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(
                                R.string.back
                            )
                            )
                        }
                    }
                )
            }, floatingActionButton = {
                FloatingActionButton(onClick = {navController.navigate(Screen.INVITEUSER.name) }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
                }
            }
        ){
            Box(
                Modifier
                    .pullRefresh(state)
                    .padding(it)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = listState
                ) {
                    item {
                        Text(text = topBarQuery, textAlign = TextAlign.Center)
                    }
                    if(userList.isNullOrEmpty()){
                        item { CircularProgressIndicator() }
                    }
                    else {
                        items(filteredDevices.take(itemsToDisplay)) { user ->
                            var showDialog by remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    //.height(200.dp)
                                    .padding(5.dp)

                                    .widthIn(max = 500.dp)
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer
                                            .copy(alpha = 0.3f),
                                        RoundedCornerShape(15.dp)
                                    )
                                    .combinedClickable(
                                        onLongClick = { showDialog = true },
                                        onClick = {
//                                                navController.navigate(Screen.INVITEUSER.name) //todo jeszcze nie wiem w sumie xD
                                        }),
                                contentAlignment = Alignment.Center,
                                propagateMinConstraints = true
                            ) {
                                UserCard(user = user)
                            }
                            if (showDialog) {
                                Dialog(onDismissRequest = { showDialog = false }) {
                                    Column(
                                        modifier = Modifier
                                            .width(280.dp) // Set the width as per your requirement
                                            .height(200.dp) // Set the height as per your requirement
                                            .background(MaterialTheme.colorScheme.background)
                                    ) {
//                                        TextButton(
//                                            onClick = {
//
//                                                showDialog = false
//                                            },
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .weight(1f)
//                                        ) {
//                                            Text("Add status")
//                                        }
                                        if(user.role=="ADMIN"){
                                            TextButton(
                                                onClick = {
                                                    viewModel.promoteToUser(user.id)
                                                    showDialog = false
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)


                                            ) {
                                                Text(stringResource(R.string.promote_to_user))
                                            }
                                        }
                                        if(user.role=="USER"){
                                            TextButton(
                                                onClick = {
                                                    viewModel.promoteToAdmin(user.id)
                                                    showDialog = false
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)


                                            ) {
                                                Text(stringResource(R.string.promote_to_admin))
                                            }
                                        }

                                        TextButton(
                                            onClick = {
                                                viewModel.sharedViewModel.deleteUserById(user.id)
                                                showDialog = false
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        ) {
                                            Text(stringResource(R.string.delete))
                                        }

                                    }
                                }
                            }
                        }
                    }

                }
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

            }
        }
    }
}