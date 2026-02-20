package com.example.estacionamientofacilapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// MODELO PARA LA BASE DE DATOS
data class UsuarioApp(
    val id: String = "",
    val nombre: String = "",
    val rol: String = ""
)

object UsuariosProvider {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, clave: String): Task<AuthResult> = auth.signInWithEmailAndPassword(email, clave)
    fun registrar(email: String, clave: String): Task<AuthResult> = auth.createUserWithEmailAndPassword(email, clave)
    fun recuperarClave(email: String): Task<Void> = auth.sendPasswordResetEmail(email)
    fun logout() = auth.signOut()
    fun usuarioActual() = auth.currentUser

    private val dbRef = FirebaseDatabase.getInstance().getReference("usuarios_personal")

    // Escuchar lista de personal
    fun escucharUsuarios(onDatosCargados: (List<UsuarioApp>) -> Unit) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<UsuarioApp>()
                for (child in snapshot.children) {
                    val u = child.getValue(UsuarioApp::class.java)
                    u?.let { lista.add(it) }
                }
                onDatosCargados(lista)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun agregarUsuarioDb(nombre: String, rol: String) {
        val id = dbRef.push().key ?: return
        val nuevo = UsuarioApp(id, nombre, rol)
        dbRef.child(id).setValue(nuevo)
    }

    // Eliminar personal
    fun eliminarUsuarioDb(id: String) {
        dbRef.child(id).removeValue()
    }
}