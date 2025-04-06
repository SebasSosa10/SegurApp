package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.controller.CommentController
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.MyReportsController
import com.example.projectappmovil.controller.ReportsAdminController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsAdmin(navController: NavController){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "REPORTES",
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
                        onClick = { CreateReportController.GlobalData.notification.value = 0 },
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
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.InicioAdminScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = true,
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
        LoadImage(innerPadding, navController)

    }
}

@Composable
fun LoadImage(innerPadding: PaddingValues, navController: NavController) {
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
    ListReports(reports = reports, innerPadding, navController)
}

@Composable
fun ListReports(reports: List<Report2>, innerPadding: PaddingValues, navController: NavController) {
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
                colors = CardDefaults.cardColors(Color.DarkGray)

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
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(text = "Titulo: " + report.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        )
                    Text(text = "Categoria: " + report.categoria,
                        fontSize = 12.sp,
                        )
                    Text(text = "Descripcion: "+report.description,
                        fontSize = 12.sp,
                        )
                    Text(text = "Ubicacion: "+report.ubication,
                        fontSize = 12.sp,
                        )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Text("Verificado:",
                            modifier = Modifier
                                .padding(top = 12.dp)
                        )
                        Checkbox(
                            checked = report.check,
                            onCheckedChange = {},
                        )
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            onClick = {navController.navigate(route = AppScreens.CommentsScreen.createRoute(report.idReport))},
                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(25.dp)
                            )

                        }
                        val check = ReportsAdminController()
                        Button(
                            onClick = { check.updateCheck(report.idReport) },

                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(text = "Verificar")
                        }
                        val delete = MyReportsController()
                        Button(
                            onClick = {delete.deleteReport(report.idReport, report.imageUrl!!)},
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}