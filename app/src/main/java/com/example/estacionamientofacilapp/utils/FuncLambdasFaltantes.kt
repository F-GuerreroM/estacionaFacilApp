package com.example.estacionamientofacilapp.utils

import android.util.Log

// REQUERIMIENTO 1: Propiedad de Extensión
// Permite llamar a "Texto".esPatenteValida directamente
val String.esPatenteValida: Boolean
    get() = this.replace("-", "").trim().length >= 6

// REQUERIMIENTO 2: Función Inline Propia
// Ejecuta un bloque de código de forma segura (try-catch reutilizable)
inline fun ejecutarSeguro(bloque: () -> Unit, onError: (Exception) -> Unit) {
    try {
        bloque()
    } catch (e: Exception) {
        Log.e("EstacionaFacil", "Error controlado", e)
        onError(e)
    }
}

// REQUERIMIENTO 3: Lambda con Etiqueta (Labeled Lambda)
// Recorre una lista y corta el flujo específico si encuentra un error
fun validarCamposObligatorios(campos: List<String>): Boolean {
    var todoOk = true

    // "validacion@" es la etiqueta que definimos
    campos.forEach validacion@{ campo ->
        if (campo.isBlank()) {
            todoOk = false
            return@validacion // Sale solo de esta iteración de la lambda
        }
    }
    return todoOk
}