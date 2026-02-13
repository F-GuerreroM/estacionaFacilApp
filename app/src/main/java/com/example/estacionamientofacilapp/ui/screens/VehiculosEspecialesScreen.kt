package com.example.estacionamientofacilapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.estacionamientofacilapp.data.VehiculosEspecialesProvider
import com.example.estacionamientofacilapp.data.VehiculoEspecial

@Composable
fun VehiculosEspecialesScreen(navController: NavController) {
    val context = LocalContext.current
    var listaVehiculos by remember { mutableStateOf(VehiculosEspecialesProvider.obtenerVehiculos()) }
    var titulo by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    var tipoSeleccionado by remember { mutableStateOf("Escolar") }

    var mostrarFormulario by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFF2C3E50),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarFormulario = !mostrarFormulario },
                containerColor = Color(0xFFF1C40F),
                contentColor = Color.Black
            ) {
                Icon(if (mostrarFormulario) Icons.Default.Close else Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C)), // Rojo para volver
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("VOLVER AL MENÚ", fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            Text("Vehículos Especiales", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Accesos recurrentes y permisos", fontSize = 14.sp, color = Color.LightGray)

            Spacer(Modifier.height(16.dp))

            if (mostrarFormulario) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E)),
                    border = BorderStroke(1.dp, Color(0xFFF1C40F)),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nuevo Permiso", color = Color(0xFFF1C40F), fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = titulo, onValueChange = { titulo = it }, label = { Text("Descripción (Ej: Ambulancia)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = horario, onValueChange = { horario = it }, label = { Text("Horario (Ej: 24/7)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(selected = tipoSeleccionado == "Escolar", onClick = { tipoSeleccionado = "Escolar" }, label = { Text("Escolar") })
                            FilterChip(selected = tipoSeleccionado == "Proveedor", onClick = { tipoSeleccionado = "Proveedor" }, label = { Text("Proveedor") })
                            FilterChip(selected = tipoSeleccionado == "Emergencia", onClick = { tipoSeleccionado = "Emergencia" }, label = { Text("Emergencia") })
                        }

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (titulo.isNotEmpty() && horario.isNotEmpty()) {
                                    VehiculosEspecialesProvider.agregarVehiculo(titulo, horario, tipoSeleccionado)
                                    listaVehiculos = VehiculosEspecialesProvider.obtenerVehiculos()
                                    titulo = ""; horario = ""
                                    mostrarFormulario = false
                                    Toast.makeText(context, "Vehículo Agregado", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("GUARDAR PERMISO")
                        }
                    }
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listaVehiculos, key = { it.id }) { vehiculo ->
                    PermisoCard(vehiculo) {
                        VehiculosEspecialesProvider.eliminarVehiculo(vehiculo)
                        listaVehiculos = VehiculosEspecialesProvider.obtenerVehiculos()
                    }
                }
            }
        }
    }
}

@Composable
fun PermisoCard(vehiculo: VehiculoEspecial, onDelete: () -> Unit) {
    // CORRECCIÓN DE ÍCONOS: Usamos los del paquete "Core" (Básico)
    val icono: ImageVector = when(vehiculo.tipo) {
        "Escolar" -> Icons.Default.Face
        "Proveedor" -> Icons.Default.ShoppingCart
        "Emergencia" -> Icons.Default.Warning
        else -> Icons.Default.Email
    }

    val colorBorde = when(vehiculo.tipo) {
        "Escolar" -> Color(0xFFF1C40F) // Amarillo
        "Proveedor" -> Color(0xFF3498DB) // Azul
        else -> Color.White
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E)),
        border = BorderStroke(2.dp, colorBorde),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(colorBorde.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorBorde, modifier = Modifier.size(32.dp))
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(vehiculo.titulo, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Surface(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "🕒 ${vehiculo.horario}",
                        color = Color(0xFFBDC3C7),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFF7F8C8D))
            }
        }
    }
}