package com.example.estacionamientofacilapp.data

// Modelo de datos
data class UsuarioApp(val id: Int, val nombre: String, val clave: String, val rol: String)

object UsuariosProvider {

    // Lista de usuarios ACTIVOS
    private val listaUsuarios = mutableListOf(
        UsuarioApp(1, "admin", "1234", "Administrador"),
        UsuarioApp(2, "juan", "0000", "Guardia")
    )

    // Lista de SOLICITUDES
    private val listaSolicitudes = mutableListOf<UsuarioApp>()

    fun obtenerUsuarios(): List<UsuarioApp> = listaUsuarios.toList()
    fun obtenerSolicitudes(): List<UsuarioApp> = listaSolicitudes.toList()

    fun agregarUsuario(nombre: String, clave: String, rol: String) {
        // Busca el ID máximo y suma 1. Si no hay, empieza en 1.
        val nuevoId = (listaUsuarios.maxOfOrNull { it.id } ?: 0) + 1
        listaUsuarios.add(UsuarioApp(nuevoId, nombre, clave, rol))
    }

    fun eliminarUsuario(usuario: UsuarioApp) {
        // Borramos buscando por ID para ser más precisos
        listaUsuarios.removeAll { it.id == usuario.id }
    }

    fun validarLogin(usuario: String, contra: String): Boolean {
        return listaUsuarios.any { it.nombre == usuario && it.clave == contra }
    }

    // --- MÉTODOS PARA SOLICITUDES ---

    fun enviarSolicitud(nombre: String, clave: String) {
        val tempId = (listaSolicitudes.minOfOrNull { it.id } ?: 0) - 1
        listaSolicitudes.add(UsuarioApp(tempId, nombre, clave, "Solicitante"))
    }

    fun aprobarSolicitud(solicitud: UsuarioApp, rolAsignado: String) {
        agregarUsuario(solicitud.nombre, solicitud.clave, rolAsignado)
        listaSolicitudes.removeAll { it.id == solicitud.id }
    }

    fun rechazarSolicitud(solicitud: UsuarioApp) {
        listaSolicitudes.removeAll { it.id == solicitud.id }
    }
}