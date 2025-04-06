package com.example.projectappmovil
import androidx.activity.compose.rememberLauncherForActivityResult
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.NotificationController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun CreateReportEmerge(navController: NavController) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val user = auth.currentUser

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "CREAR REPORTE EMERGENCIA",
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        var titulo by remember { mutableStateOf("") }
        var categoria by remember { mutableStateOf("Emergencias") }
        var descripcion by remember { mutableStateOf("") }
        var ubicacion by remember { mutableStateOf("") }
        var nombre by remember { mutableStateOf("") }

        var tituloError by remember { mutableStateOf(false) }
        var descripcionError by remember { mutableStateOf(false) }
        var ubicacionError by remember { mutableStateOf(false) }

        LaunchedEffect(user?.email) {
            db.collection("usuarios")
                .whereEqualTo("email", user?.email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0]
                        nombre = document.getString("nombre") ?: ""
                    }
                }
        }

        TextField(
            value = titulo,
            onValueChange = { titulo = it; tituloError = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .then(if (tituloError) Modifier.border(2.dp, Color.Red, RoundedCornerShape(5.dp)) else Modifier),
            label = { Text("Título") }
        )

        TextField(
            value = categoria,
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it; descripcionError = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .then(if (descripcionError) Modifier.border(2.dp, Color.Red, RoundedCornerShape(5.dp)) else Modifier),
            label = { Text("Descripción") }
        )

        TextField(
            value = ubicacion,
            onValueChange = { ubicacion = it; ubicacionError = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .then(if (ubicacionError) Modifier.border(2.dp, Color.Red, RoundedCornerShape(5.dp)) else Modifier),
            label = { Text("Ubicación") }
        )

        Spacer(modifier = Modifier.height(30.dp))

        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                imageUri = uri
            }
        )

        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        } else {
            Text("No image selected")
        }

        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Seleccionar imagen")
        }

        Spacer(modifier = Modifier.height(10.dp))

        val createReportController = CreateReportController()
        val currentUser = FirebaseAuth.getInstance()
        val userId = currentUser.uid
        val email = currentUser.currentUser?.email

        var showErrorDialog by remember { mutableStateOf(false) }
        var showSuccessDialog by remember { mutableStateOf(false) }

        Button(
            onClick = {
                tituloError = titulo.isEmpty()
                descripcionError = descripcion.isEmpty()
                ubicacionError = ubicacion.isEmpty()

                if (tituloError || descripcionError || ubicacionError) {
                    showErrorDialog = true
                } else if (userId != null && imageUri != null && email != null) {
                    imageUri?.let { uri ->
                        createReportController.saveReportImageToFirebaseStorage(
                            userId, titulo, categoria, descripcion, ubicacion, uri, nombre
                        )
                        CreateReportController.GlobalData.notification.value += 1

                        showSuccessDialog = true
                        titulo = ""
                        descripcion = ""
                        ubicacion = ""
                        imageUri = null
                    }
                }
            },
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(text = "CREAR REPORTE")
        }

        Text(
            text = "INICIAR SESIÓN",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 80.dp)
                .clickable { navController.popBackStack() }
        )

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text("Debe llenar todos los campos requeridos.") },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Correcto") },
                text = { Text("Se creó el reporte correctamente!") },
                confirmButton = {
                    Button(onClick = { showSuccessDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}
