package com.example.estacionamientofacilapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.estacionamientofacilapp.navigation.AppNavigation
import com.example.estacionamientofacilapp.ui.theme.EstacionamientoFacilAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstacionamientoFacilAppTheme {

                AppNavigation()
            }
        }
    }
}