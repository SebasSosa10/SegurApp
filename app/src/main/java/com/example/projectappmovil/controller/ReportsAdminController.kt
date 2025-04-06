package com.example.projectappmovil.controller

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReportsAdminController {
    fun updateCheck(idReport: String){
        val db = Firebase.firestore
        db.collection("reportes")
            .document(idReport)
            .update("check", true)
            .addOnSuccessListener {
                println("Check actualizado")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar check: $e")
            }
    }
}