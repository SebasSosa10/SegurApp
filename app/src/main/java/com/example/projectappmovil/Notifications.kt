package com.example.projectappmovil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notification(navController: NavController){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notificaciones")},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        LoadReports(innerPadding)
    }
}

@Composable
fun LoadReports(innerPadding: PaddingValues) {
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
    ListNotifications(reports = reports, innerPadding)
}

@Composable
fun ListNotifications(reports: List<Report2>, innerPadding: PaddingValues) {
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

                }
                Column {
                    Text(text = "Acaba de subir un reporte de: " + report.categoria,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp))
                }
            }
        }
    }
}