    package com.example.projectappmovil

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.material3.AlertDialog
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
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
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import com.example.projectappmovil.controller.LoginController
    import com.example.projectappmovil.navegation.AppScreens


    @Composable
    fun ImageLogin(){
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Imagen Inicio",
            modifier = Modifier.size(200.dp),
        )
    }

    @Composable
    fun Titulo(){
        Text(
            text = "Bienvenido a SegurApp",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun Body(navController: NavController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var email by remember { mutableStateOf("") }
            var emailError by remember { mutableStateOf(false) }

            TextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                    emailError = false
                },
                label = { Text("EMAIL") },
                isError = emailError,
                modifier = Modifier
                    .border(2.dp, if (emailError) Color.Red else Color.Transparent)
            )

            Spacer(modifier = Modifier.height(20.dp))

            var password by remember { mutableStateOf("") }
            var passwordError by remember { mutableStateOf(false) }

            TextField(
                value = password,
                onValueChange = { newPass ->
                    password = newPass
                    passwordError = false
                },
                label = { Text("PASSWORD") },
                isError = passwordError,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .border(2.dp, if (passwordError) Color.Red else Color.Transparent)
            )

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate(route = AppScreens.ForgotScreen.route) }
            )

            var showErrorDialog by remember { mutableStateOf(false) }
            var errorMessage by remember { mutableStateOf("") }

            val loginController = LoginController()

            Button(
                onClick = {
                    emailError = email.isBlank()
                    passwordError = password.isBlank()

                    if (emailError || passwordError) {
                        errorMessage = "Debes llenar todos los campos"
                        showErrorDialog = true
                    } else {
                        loginController.iniciarSesion(email, password, navController) { error ->
                            errorMessage = error
                            showErrorDialog = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(height = 50.dp, width = 250.dp)
            ) {
                Text("INICIAR SESIÓN")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(route = AppScreens.RegisterScreen.route) },
                modifier = Modifier.size(height = 50.dp, width = 250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("REGISTRARSE")
            }

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "REPORTE DE EMERGENCIA",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(route = AppScreens.CreateReportEmercyScreen.route)
                    }
                )
            }

            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("ERROR") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        Button(onClick = { showErrorDialog = false }) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }


    @Composable
    fun PreviewLogin(navController: NavController){
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            ImageLogin()
            Titulo()
            Spacer(modifier = Modifier.height(60.dp))
            Body(navController)
        }
    }





