package com.example.projectappmovil.controller

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotificationController {
    val db = Firebase.firestore
    fun updateNotification() {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val batch = db.batch()
                for (document in querySnapshot.documents) {
                    val documentRef = document.reference
                    batch.update(documentRef, "countNotifi", FieldValue.increment(1))
                }
            }
    }

    fun updateClear(userId: String){
        db.collection("reportes")
            .document(userId)
            .update("countNotifi", 0)
            .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }

    }
}