package com.example.estacionamientofacilapp.data

data class ClienteParking(
    val nombre: String,
    val patente: String,
    val horaEntrada: String,
    val isMensual: Boolean
)

val listaClientes = mutableListOf(
    ClienteParking("Juan Perez", "FFDD22", "08:00", true),
    ClienteParking("Maria Lopez", "GGHH11", "08:15", false),
    ClienteParking("Carlos Ruiz", "JKLM99", "09:30", true),
    ClienteParking("Ana Torres", "ZZYY00", "10:00", false),
    ClienteParking("Pedro Diaz", "AABB12", "11:45", true)
)