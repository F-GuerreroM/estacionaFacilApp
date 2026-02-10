package com.example.estacionamientofacilapp.data

import androidx.compose.runtime.mutableStateListOf

// Modelo de Usuario
data class Usuario(val username: String, val password: String)


val usuarios = mutableStateListOf(
    Usuario("admin", "1234"),
    Usuario("user", "abcd"),
    Usuario("profe", "70")
)