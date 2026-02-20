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
import com.example.estacionamientofacilapp.data.UsuarioApp
import com.example.estacionamientofacilapp.data.UsuariosProvider

@Composable
fun UsuariosScreen(navController: NavController) {
    val context = LocalContext.current

    // ESTADO: Lista de personal desde Firebase
    var listaUsuarios by remember { mutableStateOf(emptyList<UsuarioApp>()) }

    // CONEXIÓN FIREBASE
    DisposableEffect(Unit) {
        UsuariosProvider.escucharUsuarios { datos ->
            listaUsuarios = datos
        }
        onDispose { }
    }

    // Variables del formulario
    var nombre by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }

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
                Text("Gestión de Personal", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("Base de datos de empleados (Firebase)", fontSize = 14.sp, color = Color.LightGray)
            }

            // --- FORMULARIO CREAR USUARIO (SOLO BD) ---
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Registrar Empleado", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = rol, onValueChange = { rol = it }, label = { Text("Cargo / Rol") },
                            placeholder = { Text("Ej: Guardia, Admin") },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (nombre.isNotEmpty() && rol.isNotEmpty()) {
                                    // Guardamos en la Base de Datos
                                    UsuariosProvider.agregarUsuarioDb(nombre, rol)
                                    nombre = ""; rol = ""
                                    Toast.makeText(context, "Empleado Registrado", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Complete los datos", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("AGREGAR A LISTA")
                        }
                    }
                }
            }

            item {
                Text("Personal Activo", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=16.dp))
            }

            // --- LISTADO DESDE FIREBASE ---
            if (listaUsuarios.isEmpty()) {
                item { Text("Cargando o sin datos...", color = Color.Gray) }
            }

            items(listaUsuarios) { usuario ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E))) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(usuario.nombre, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Surface(
                                color = Color(0xFFF1C40F).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(top=4.dp)
                            ) {
                                Text(
                                    text = usuario.rol,
                                    color = Color(0xFFF1C40F),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        IconButton(onClick = {
                            UsuariosProvider.eliminarUsuarioDb(usuario.id)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}