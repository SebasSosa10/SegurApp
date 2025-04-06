package com.example.projectappmovil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Password(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿OLVIDASTE TU CONTRASEÑA?",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 25.dp),
            fontWeight = FontWeight.Bold
        )

        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text("EMAIL") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.9f),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            }
        )

        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text("NUEVA CONTRASEÑA") },
            visualTransformation = PasswordVisualTransformation(), // Oculta la contraseña
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.9f),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            }
        )

        // esta alerta es la misma del login la traje para aca
        var showErrorDialog by remember { mutableStateOf(false) }
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("ERROR") },
                text = { Text("Debes de llenar todos los campos")},
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }

        // implemente la alerta a este boton ya que este valida los campos vacios
        // es una alerta de toda la vida campos vacios
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    showErrorDialog = true
                } else {
                    // toca ver donde se dirige este boton
                }
            },
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(text = "CONFIRMAR")
        }


        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = "INICIAR SESIÓN")
        }
    }
}
