
package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllReports(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.InicioScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.AllReportsScreen.route)},
                    selected = true,
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
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null)},
                    label = { Text("PROFILE") }
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reportes",
                    fontSize = 20.sp
                ) },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo2),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton (
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
                        Badges(CreateReportController.GlobalData.notification.value)

                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        }
    ) { innerPadding ->
        LoadImageFromFirestore3(innerPadding, navController)
    }
}


@Composable
fun Badges(count: Int) {
    if (count > 0) {
        Box(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .offset(x = 9.dp, y = (-9).dp)
                .clip(CircleShape)
                .background(Color.Red)
                .size(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun LoadImageFromFirestore3(innerPadding: PaddingValues, navController: NavController) {
    val db = Firebase.firestore
    var reports by remember { mutableStateOf<List<Report2>>(emptyList()) }

    LaunchedEffect(Unit) {
        db.collection("reportes")
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                    println("Error listening to Firestore: ${exception.message}")
                    return@addSnapshotListener
                }
                val newReports = snapshot?.documents?.mapNotNull { document ->
                    Report2(
                        imageUrl = document.getString("imageUrl"),
                        title = document.getString("titulo") ?: "",
                        categoria = document.getString("categoria") ?: "",
                        description = document.getString("descripcion") ?: "",
                        ubication = document.getString("ubicacion") ?: "",
                        nombre = document.getString("nombre") ?: "",
                        idReport = document.getString("idReport") ?: "",
                        check = document.getBoolean("check") ?: false,
                        countMessages = document.getLong("countMessages")?.toInt() ?: 0

                    )
                } ?: emptyList()
                reports = newReports
            }
    }
    MyLazyColumn2(reports = reports, innerPadding, navController)
}

data class Report2(
    val imageUrl: String?,
    val title: String,
    val categoria: String,
    val description: String,
    val ubication: String,
    val nombre: String,
    val idReport: String,
    val check: Boolean,
    val countMessages: Int
)

@Composable
fun MyLazyColumn2(reports: List<Report2>, innerPadding: PaddingValues, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {

        items(reports) { report ->
            Card(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.Gray)

            ) {
                Column {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = Color.Black,
                        )
                        Text(
                            text = "By: ${report.nombre}",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Image(
                        painter = rememberAsyncImagePainter(report.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                }
                Column (
                    modifier = Modifier
                        .background(color = Color.DarkGray),
                ) {
                    Text(text = "Titulo: " + report.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                    Text(text = "Categoria: " + report.categoria,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp))
                    Text(text = "Descripcion: "+report.description,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp))
                    Text(text = "Ubicacion: "+report.ubication,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp))
                    Row (
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                        ){
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        IconButton(
                            onClick = {navController.navigate(route = AppScreens.CommentsScreen.createRoute(report.idReport))},
                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                        ){
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(25.dp)
                            )
                            Badges(report.countMessages)
                        }


                        Row {
                            Text("Verificado",
                                modifier = Modifier
                                    .padding(top = 12.dp, start = 7.dp)
                            )
                            Checkbox(
                                checked = report.check,
                                onCheckedChange = {},

                                )
                        }
                    }
                }
            }
        }
    }
}





