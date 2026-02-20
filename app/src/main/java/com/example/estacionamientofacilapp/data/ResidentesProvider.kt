package com.example.estacionamientofacilapp.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class ResidenteApp(
    val id: String = "",
    val nombre: String = "",
    val patente: String = "",
    val dpto: String = ""
)

object ResidentesProvider {

    private val dbRef = FirebaseDatabase.getInstance().getReference("residentes")

    // ESCUCHAR EN TIEMPO REAL (Read)
    fun escucharResidentes(onDatosCargados: (List<ResidenteApp>) -> Unit) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaTemp = mutableListOf<ResidenteApp>()
                for (dato in snapshot.children) {
                    val residente = dato.getValue(ResidenteApp::class.java)
                    residente?.let { listaTemp.add(it) }
                }
                onDatosCargados(listaTemp)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    // AGREGAR (Create)
    fun agregarResidente(nombre: String, patente: String, dpto: String) {
        val id = dbRef.push().key ?: return // Genera ID único (ej: -Msj82...)
        val nuevoResidente = ResidenteApp(id, nombre, patente, dpto)
        dbRef.child(id).setValue(nuevoResidente)
    }

    // ELIMINAR (Delete)
    fun eliminarResidente(id: String) {
        dbRef.child(id).removeValue()
    }
}