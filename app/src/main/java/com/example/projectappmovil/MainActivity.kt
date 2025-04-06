package com.example.projectappmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projectappmovil.navegation.AppNavigation
import com.example.projectappmovil.ui.theme.ProjectAppMovilTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectAppMovilTheme {
                FirebaseApp.initializeApp(this)
                AppNavigation()
            }
        }
    }
}
