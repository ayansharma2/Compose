package com.ayan.composeexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ayan.composeexamples.ui.theme.ComposeExamplesTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeActivity : AppCompatActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = remember { mutableStateOf(false) }
            ComposeExamplesTheme(darkTheme.value) {
                createView(darkTheme)
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun createView(systemDarkTheme: MutableState<Boolean>) {
    val darkTheme = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val homeBottom = remember { mutableStateOf(Screen.Screen1) }
    val currentHome = remember { mutableStateOf("Home") }
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    Scaffold(scaffoldState = scaffoldState,
        drawerShape = RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp),
        floatingActionButton = { FAB(scaffoldState, scope) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        drawerContent = {

            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    text = "Screen 1", modifier = Modifier
                        .padding(top = 12.dp)

                        .clickable {
                            homeBottom.value = Screen.Screen1
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }, color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "Screen 2", modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable {
                            homeBottom.value = Screen.Screen2
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }, color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "Screen 3", modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable {
                            homeBottom.value = Screen.Screen3
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }, color = MaterialTheme.colors.onPrimary
                )
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colors.primary),
                title = {
                    Text(
                        text =
                        when (homeBottom.value) {
                            Screen.Screen1 -> "Screen 1"
                            Screen.Screen2 -> "Screen 2"
                            Screen.Screen3 -> "Screen 3"
                        }, color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Open",
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                            .padding(start = 15.dp),
                        tint = MaterialTheme.colors.onPrimary)
                }
            )
        },
        bottomBar = {
            BottomNavBar(currentHome)
        }) {

        Column(modifier = Modifier.verticalScroll(enabled = true, state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = darkTheme.value,
                    onCheckedChange = { value ->
                        systemDarkTheme.value = value
                        darkTheme.value = value
                    },
                )
                Text(
                    text = "Enable Dark Mode", color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
            when (currentHome.value) {
                "Home" -> {
                    Home(LocalContext)
                }
                "Profile" -> {
                    Profile(scope,scaffoldState)
                }
            }
        }
    }
}

@Composable
fun FAB(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    FloatingActionButton(
        onClick = {
            scope.launch {
                Log.e("SnackBar", "SnackBar Showed")
                scaffoldState.snackbarHostState.showSnackbar(message = "Floating Action Button Clicked")
            }
        },
        shape = RoundedCornerShape(50),
        backgroundColor = MaterialTheme.colors.primary,

        ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Null")
    }
}

@Composable
fun BottomNavBar(currentHome: MutableState<String>) {
    BottomAppBar(cutoutShape = RoundedCornerShape(50)) {

        Utils.bottomNavItems.forEach { item ->
            BottomNavigationItem(selected = currentHome.value == item.name,
                onClick = { currentHome.value = item.name },
                icon = { Icon(imageVector = item.image, contentDescription = "icon") },
                label = { Text(text = item.name) }
            )
        }
    }
}


enum class Screen {
    Screen1, Screen2, Screen3
}