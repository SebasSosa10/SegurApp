package com.example.projectappmovil.controller

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class CreateReportController {
    val db = Firebase.firestore
    fun saveReport(
        userId: String, titulo: String, categoria: String, descripcion: String,
        ubicacion: String, imageUrl: String, name: String
    ) {
        val report = hashMapOf(
            "titulo" to titulo,
            "categoria" to categoria,
            "descripcion" to descripcion,
            "ubicacion" to ubicacion,
            "imageUrl" to imageUrl,
            "nombre" to name,
            "userId" to userId,
            "check" to false,
            "countMessages" to 0
        )
        db.collection("reportes")
            .add(report)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: ${documentReference.id}")
                val id = documentReference.id
                db.collection("reportes")
                    .document(id)
                    .update("idReport", id)
                GlobalData.notification.value += 1

            }
    }

    object GlobalData {
        val notification =  mutableStateOf(0)
    }

    fun saveReportImageToFirebaseStorage(
        userId: String,
        titulo: String,
        categoria: String,
        descripcion: String,
        ubicacion: String,
        imageUri: Uri,
        nombre: String,
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageRef = storageRef.child("/$userId/images/${UUID.randomUUID()}.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                saveReport(userId, titulo, categoria, descripcion, ubicacion, imageUrl, nombre)
            }
        }.addOnFailureListener { exception ->
            println("Error uploading image: ${exception.message}")
        }
    }

    fun updateCountMessage(reportId: String){
        db.collection("reportes")
            .document(reportId)
            .update("countMessages", FieldValue.increment(1))
            .addOnSuccessListener { println("countMessages updated successfully") }
    }
}