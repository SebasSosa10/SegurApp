package com.example.projectappmovil.controller

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MyReportsController {
    fun deleteReport(reportId: String, imageUrl: String) {
        val db = Firebase.firestore
        db.collection("reportes")
            .document(reportId)
            .delete()
            .addOnSuccessListener {
                println("Reporte eliminado exitosamente")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar el reporte: $e")
            }
        deleteImageFromStorage(imageUrl)
    }

    fun deleteImageFromStorage(imageUrl: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl(imageUrl)

        storageRef.delete()
            .addOnSuccessListener {
                println("Imagen eliminada exitosamente")
            }
            .addOnFailureListener { exception ->
                println("Error al eliminar la imagen: ${exception.message}")
            }
    }

    fun editReport(reportId: String, title: String, categoria: String, description: String, ubication: String){
        val db = Firebase.firestore
        val data = mapOf(
            "titulo" to title,
            "categoria" to categoria,
            "descripcion" to description,
            "ubicacion" to ubication
            )
        db.collection("reportes")
            .document(reportId)
            .update(data)
            .addOnSuccessListener {
                println("Reporte editado exitosamente")
            }
            .addOnFailureListener { e ->
                println("Error al editar el reporte: $e")
            }

    }
}