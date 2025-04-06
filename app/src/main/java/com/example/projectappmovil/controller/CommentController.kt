package com.example.projectappmovil.controller

import androidx.compose.runtime.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentController {
    val db = Firebase.firestore

    fun saveComment(
        nombre: String, descripcion: String, userId: String, idReport: String
    ) {
        val comentario = hashMapOf(
            "nombre" to nombre,
            "descripcion" to descripcion,
            "userId" to userId,
            "idReport" to idReport
        )
        db.collection("reportes")
            .document(idReport)
            .collection("comentarios")
            .add(comentario)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: ${documentReference.id}")
            }
    }
}

