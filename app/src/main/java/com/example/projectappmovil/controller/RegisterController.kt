package com.example.projectappmovil.controller

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class RegisterController {
    val db = Firebase.firestore
    fun agregarClienteFirestore(
        userId: String,
        nombre: String,
        ciudad: String,
        direccion: String,
        email: String,
        contrasenia: String
    ) {
        val cliente = hashMapOf(
            "nombre" to nombre,
            "ciudad" to ciudad,
            "direccion" to direccion,
            "email" to email,
            "contrasenia" to contrasenia,
            "rol" to "cliente"
        )
        db.collection("usuarios")
            .document(userId)
            .set(cliente)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: $documentReference")
            }
    }
    fun agregarClienteAuth(nombre: String, ciudad: String, direccion: String, email: String, password: String){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val userId = user?.uid
                    if (userId != null) {
                        agregarClienteFirestore(userId, nombre, ciudad, direccion, email, password)
                    }
                    println("Registro exitoso")
                } else {
                    println("Error en el registro: ${task.exception?.message}")
                }
        }
    }


//    fun agregarAdministrador(userId: String){
//        val db = Firebase.firestore
//        val admin = hashMapOf(
//            "nombre" to "Luis",
//            "ciudad" to "Manizales",
//            "direccion" to "Calle 456",
//            "email" to "admin@eam.com",
//            "contrasenia" to "admin123",
//            "rol" to "admin"
//        )
//        db.collection("usuarios")
//            .document(userId)
//            .set(admin)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot written with ID: $documentReference")
//            }
//            .addOnFailureListener{ e ->
//                println("Error adding document $e")
//            }
//    }
}