package com.example.projectappmovil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.NotificationController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inicio(navController: NavController){

    val countNotifi = NotificationController()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Scaffold (
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {},
                    selected = true,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.AllReportsScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTS") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.MyReportsScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("MINE") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.ProfileScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text("PROFILE") }
                )
            }
        },

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inicio",
                    fontSize = 20.sp
                )},
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo2),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton (
                        onClick = {navController.navigate(route = AppScreens.NotificationScreen.route)
                            countNotifi.updateClear(userId!!)},
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Badges(CreateReportController.GlobalData.notification.value)

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var searchQuery by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 5.dp, top = 16.dp),
                    placeholder = { Text("Buscar...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White
                    )
                )
                Image(
                    painter = painterResource(R.drawable.mapa),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = { navController.navigate(route = AppScreens.CreateReportScreen.route) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(height = 50.dp, width = 300.dp),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(
                        "CREAR REPORTE",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Composable
fun loadCount(): List<Users1> {
    val db = Firebase.firestore
    var users by remember { mutableStateOf<List<Users1>>(emptyList()) }

    LaunchedEffect(Unit) {
        db.collection("usuarios")
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                    println("Error listening to Firestore: ${exception.message}")
                    return@addSnapshotListener
                }
                val newUser = snapshot?.documents?.mapNotNull { document ->
                    Users1(
                        countNotifi = document.getLong("countNotifi")?.toInt() ?: 0

                    )
                } ?: emptyList()
                users = newUser
            }
    }
    return users
}

data class Users1 (
    val countNotifi: Int
        )


