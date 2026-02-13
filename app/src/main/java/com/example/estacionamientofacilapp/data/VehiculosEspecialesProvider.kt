package com.example.estacionamientofacilapp.data

// Modelo de datos
data class VehiculoEspecial(
    val id: Int,
    val titulo: String,
    val horario: String,
    val tipo: String
)

object VehiculosEspecialesProvider {

    private val lista = mutableListOf(
        VehiculoEspecial(1, "Furgón Escolar", "Lun a Vie 07:00 AM", "Escolar"),
        VehiculoEspecial(2, "Camión Coca-Cola", "Jueves 10:00 AM", "Proveedor")
    )

    fun obtenerVehiculos(): List<VehiculoEspecial> = lista.toList()

    fun agregarVehiculo(titulo: String, horario: String, tipo: String) {
        val nuevoId = (lista.maxOfOrNull { it.id } ?: 0) + 1
        lista.add(VehiculoEspecial(nuevoId, titulo, horario, tipo))
    }

    fun eliminarVehiculo(vehiculo: VehiculoEspecial) {
        lista.removeAll { it.id == vehiculo.id }
    }
}