package com.example.estacionamientofacilapp.data

// Modelo de Datos (La estructura de la tabla)
data class ResidenteApp(val id: Int, val nombre: String, val patente: String, val dpto: String)

// El Provider (Gestor de Datos Singleton)
object ResidentesProvider {

    private val listaResidentes = mutableListOf(
        ResidenteApp(1, "Juan Pérez", "AA1122", "101"),
        ResidenteApp(2, "Maria Soto", "BB3344", "205"),
        ResidenteApp(3, "Pedro Diaz", "CC5566", "303")
    )

    fun obtenerResidentes(): List<ResidenteApp> = listaResidentes.toList()

    // CREAR (Insert)
    fun agregarResidente(nombre: String, patente: String, dpto: String) {
        // Generamos ID único (Máximo actual + 1)
        val nuevoId = (listaResidentes.maxOfOrNull { it.id } ?: 0) + 1
        listaResidentes.add(ResidenteApp(nuevoId, nombre, patente, dpto))
    }

    // BORRAR (Delete)
    fun eliminarResidente(residente: ResidenteApp) {
        listaResidentes.removeAll { it.id == residente.id }
    }
}