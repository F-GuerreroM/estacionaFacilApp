package com.example.estacionamientofacilapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.estacionamientofacilapp.data.UsuariosProvider

@Composable
fun UsuariosScreen(navController: NavController) {
    val context = LocalContext.current

    var listaUsuarios by remember { mutableStateOf(UsuariosProvider.obtenerUsuarios()) }
    var listaSolicitudes by remember { mutableStateOf(UsuariosProvider.obtenerSolicitudes()) }

    var nombre by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    fun recargarDatos() {
        listaUsuarios = UsuariosProvider.obtenerUsuarios()
        listaSolicitudes = UsuariosProvider.obtenerSolicitudes()
    }

    Scaffold(
        containerColor = Color(0xFF2C3E50),
        bottomBar = {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1C40F)),
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(55.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                Text("VOLVER", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Gestión de Usuarios", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            // --- SECCIÓN SOLICITUDES ---
            if (listaSolicitudes.isNotEmpty()) {
                item {
                    Text("🔔 Solicitudes Pendientes", color = Color(0xFFF1C40F), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                items(listaSolicitudes, key = { it.id }) { solicitud ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE67E22).copy(alpha = 0.2f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE67E22)),
                        modifier = Modifier.animateItem() // Animación suave (Opcional, requiere imports extra, si falla quítalo)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(solicitud.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                                Text("Clave: ${solicitud.clave}", color = Color.LightGray, fontSize = 12.sp)
                            }
                            Row {
                                IconButton(onClick = {
                                    UsuariosProvider.aprobarSolicitud(solicitud, "Guardia")
                                    recargarDatos() // Recargamos seguro
                                    Toast.makeText(context, "Aprobado", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Default.Check, contentDescription = "Aprobar", tint = Color(0xFF27AE60))
                                }
                                IconButton(onClick = {
                                    UsuariosProvider.rechazarSolicitud(solicitud)
                                    recargarDatos() // Recargamos seguro
                                    Toast.makeText(context, "Rechazado", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Rechazar", tint = Color(0xFFE74C3C))
                                }
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(16.dp)) }
            } else {
                item {
                    Text("No hay solicitudes pendientes", color = Color.Gray, fontSize = 14.sp)
                    Spacer(Modifier.height(16.dp))
                }
            }

            // --- SECCIÓN CREAR MANUAL ---
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Crear Usuario Directamente", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = clave, onValueChange = { clave = it }, label = { Text("Clave") },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = rol, onValueChange = { rol = it }, label = { Text("Rol") },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (nombre.isNotEmpty() && clave.isNotEmpty()) {
                                    UsuariosProvider.agregarUsuario(nombre, clave, if(rol.isEmpty()) "Operario" else rol)
                                    recargarDatos()
                                    nombre = ""; clave = ""; rol = ""
                                    Toast.makeText(context, "Usuario Creado", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("AGREGAR")
                        }
                    }
                }
            }

            item {
                Text("Personal Activo", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=16.dp))
            }

            items(listaUsuarios, key = { it.id }) { usuario ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E))) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(usuario.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                            Text(usuario.rol, color = Color(0xFFF1C40F))
                        }
                        IconButton(onClick = {
                            UsuariosProvider.eliminarUsuario(usuario)
                            recargarDatos()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}