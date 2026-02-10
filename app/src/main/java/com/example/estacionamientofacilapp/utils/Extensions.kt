package com.example.estacionamientofacilapp.utils


fun String.formatearPatente(): String {
    // Si la patente tiene 6 caracteres, ponemos guiones cada 2
    return if (this.length == 6) {
        "${this.substring(0, 2)}-${this.substring(2, 4)}-${this.substring(4, 6)}"
    } else {
        this.uppercase()
    }
}