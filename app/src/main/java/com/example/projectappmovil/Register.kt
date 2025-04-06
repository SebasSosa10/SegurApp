package com.example.projectappmovil

import android.util.Patterns
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.controller.RegisterController
import kotlinx.coroutines.launch

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun Registro(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf(false) }
    var ciudadError by remember { mutableStateOf(false) }
    var direccionError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var contraseniaError by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) {
            Snackbar(
                modifier = Modifier
                    .border(1.dp, Color.White)
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth(0.8f),
                containerColor = MaterialTheme.colorScheme.primary,
                content = {
                    Text(
                        text = "SE HA REGISTRADO CORRECTAMENTE",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        } },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val screenWidth = maxWidth
                val screenHeight = maxHeight

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "REGISTRO USUARIO",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = if (screenWidth < 400.dp) 20.sp else 26.sp, // Tamaño de fuente responsive
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = if (screenHeight < 600.dp) 20.dp else 50.dp) // Padding responsive
                    )

                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it; nombreError = false },
                        label = { Text("NOMBRE") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                        isError = nombreError,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .border(2.dp, if (nombreError) Color.Red else Color.Transparent, RoundedCornerShape(8.dp))
                    )

                    TextField(
                        value = ciudad,
                        onValueChange = { ciudad = it; ciudadError = false },
                        label = { Text("CIUDAD") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                        isError = ciudadError,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .border(2.dp, if (ciudadError) Color.Red else Color.Transparent, RoundedCornerShape(8.dp))
                    )

                    TextField(
                        value = direccion,
                        onValueChange = { direccion = it; direccionError = false },
                        label = { Text("DIRECCION") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                        isError = direccionError,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .border(2.dp, if (direccionError) Color.Red else Color.Transparent, RoundedCornerShape(8.dp))
                    )

                    TextField(
                        value = email,
                        onValueChange = { email = it; emailError = false },
                        label = { Text("EMAIL") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                        isError = emailError,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .border(2.dp, if (emailError) Color.Red else Color.Transparent, RoundedCornerShape(8.dp))
                    )

                    TextField(
                        value = contrasenia,
                        onValueChange = { contrasenia = it; contraseniaError = false },
                        label = { Text("CONTRASEÑA") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
                        isError = contraseniaError,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .border(2.dp, if (contraseniaError) Color.Red else Color.Transparent, RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val registerController1 = RegisterController()
                    var showErrorDialog by remember { mutableStateOf(false) }

                    Button(
                        onClick = {
                            nombreError = false
                            ciudadError = false
                            direccionError = false
                            emailError = false
                            contraseniaError = false

                            // Validar campos vacíos
                            if (nombre.isBlank() || ciudad.isBlank() || direccion.isBlank() || email.isBlank() || contrasenia.isBlank()) {
                                nombreError = nombre.isBlank()
                                ciudadError = ciudad.isBlank()
                                direccionError = direccion.isBlank()
                                emailError = email.isBlank()
                                contraseniaError = contrasenia.isBlank()
                                showErrorDialog = true
                            }
                            else if (!isValidEmail(email)) {
                                emailError = true
                                showErrorDialog = true
                            }
                            else if (contrasenia.length < 6) {
                                contraseniaError = true
                                showErrorDialog = true
                            }
                            else {
                                registerController1.agregarClienteAuth(
                                    nombre, ciudad, direccion, email, contrasenia
                                )
                                nombre = ""; ciudad = ""; direccion = ""; email = ""; contrasenia = ""

                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Registro exitoso")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text("REGISTRARSE")
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {navController.popBackStack()},
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("INICIAR SESION")
                    }
                    if (showErrorDialog) {
                        AlertDialog(
                            onDismissRequest = { showErrorDialog = false },
                            title = { Text("ERROR") },
                            text = {
                                Text(
                                    when {
                                        nombre.isBlank() || ciudad.isBlank() || direccion.isBlank()
                                                || email.isBlank() || contrasenia.isBlank() ->
                                            "Debes llenar todos los campos."
                                        !isValidEmail(email) ->
                                            "Asegúrate de que el email tenga el formato correcto, por ejemplo:\n**tucorreo@gmail.com**."
                                        contrasenia.length < 6 ->
                                            "La contraseña debe tener al menos 6 caracteres."
                                        else -> ""
                                    }
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = { showErrorDialog = false }
                                ) {
                                    Text("Aceptar")
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}




