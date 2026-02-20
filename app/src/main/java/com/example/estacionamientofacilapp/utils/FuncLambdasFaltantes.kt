package com.example.estacionamientofacilapp.utils

import android.util.Log

val String.esPatenteValida: Boolean
    get() = this.replace("-", "").trim().length >= 6

inline fun ejecutarSeguro(bloque: () -> Unit, onError: (Exception) -> Unit) {
    try {
        bloque()
    } catch (e: Exception) {
        Log.e("EstacionaFacil", "Error controlado", e)
        onError(e)
    }
}

fun validarCamposObligatorios(campos: List<String>): Boolean {
    var todoOk = true

    campos.forEach validacion@{ campo ->
        if (campo.isBlank()) {
            todoOk = false
            return@validacion
        }
    }
    return todoOk
}