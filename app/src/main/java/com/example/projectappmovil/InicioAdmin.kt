package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.navegation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioAdmin(navController: NavController){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Inicio",
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate(route = AppScreens.NotificationScreen.route)
                            CreateReportController.GlobalData.notification.value = 0 },
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {},
                    selected = true,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.ReportsAdminScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("REPORTS") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.ProfileAdminScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text("PROFILE") }
                )
            }
        }

    ) { innerPadding ->
        Menu(innerPadding)

    }
}

@Composable
fun Menu(innerPadding: PaddingValues){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )
        Text(text = "BIENVENIDO ADMINISTRADOR",
            modifier = Modifier.padding(),
            fontSize = 25.sp
        )
    }

}