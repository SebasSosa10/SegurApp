package com.example.projectappmovil.controller

import androidx.navigation.NavController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginController {
    fun iniciarSesion(email: String, password: String, navController: NavController, onError: (String) -> Unit) {
        val auth: FirebaseAuth = Firebase.auth
        val db = Firebase.firestore

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUid = auth.currentUser?.uid

                    if (userUid != null) {
                        db.collection("usuarios")
                            .document(userUid)
                            .get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    val rol = documentSnapshot.getString("rol")
                                    when (rol) {
                                        "admin" -> navController.navigate(route = AppScreens.InicioAdminScreen.route)
                                        else -> navController.navigate(route = AppScreens.InicioScreen.route)
                                    }
                                } else {
                                    onError("Usuario o contraseña incorrectos.")
                                }
                            }
                            .addOnFailureListener {
                                onError("Error al obtener los datos del usuario. Inténtalo de nuevo.")
                            }
                    } else {
                        onError("No se pudo obtener el UID del usuario.")
                    }
                } else {
                    onError("Usuario o contraseña incorrectos.")
                }
            }
    }
}