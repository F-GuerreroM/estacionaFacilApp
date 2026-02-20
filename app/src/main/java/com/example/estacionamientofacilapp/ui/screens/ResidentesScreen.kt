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
import com.example.estacionamientofacilapp.data.ResidenteApp
import com.example.estacionamientofacilapp.data.ResidentesProvider
import com.example.estacionamientofacilapp.utils.esPatenteValida

@Composable
fun ResidentesScreen(navController: NavController) {
    val context = LocalContext.current

    var listaResidentes by remember { mutableStateOf(emptyList<ResidenteApp>()) }

    // CONEXIÓN A FIREBASE
    DisposableEffect(Unit) {
        ResidentesProvider.escucharResidentes { datos ->
            listaResidentes = datos
        }
        onDispose { }
    }

    // Campos del Formulario
    var nombre by remember { mutableStateOf("") }
    var patente by remember { mutableStateOf("") }
    var dpto by remember { mutableStateOf("") }

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
                Text("Base de Datos Residentes", fontSize = 26.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("Gestiona los vehículos (NUBE FIREBASE)", fontSize = 14.sp, color = Color.LightGray)
            }

            // --- FORMULARIO DE INGRESO ---
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nuevo Residente", color = Color(0xFFF1C40F), fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Propietario") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        Spacer(Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = patente, onValueChange = { patente = it }, label = { Text("Patente") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                            OutlinedTextField(
                                value = dpto, onValueChange = { dpto = it }, label = { Text("Dpto") },
                                modifier = Modifier.weight(0.5f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (nombre.isNotEmpty() && patente.isNotEmpty() && dpto.isNotEmpty()) {
                                    if (patente.esPatenteValida) {
                                        ResidentesProvider.agregarResidente(nombre, patente, dpto)
                                        nombre = ""; patente = ""; dpto = ""
                                        Toast.makeText(context, "Guardando en Nube...", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Patente Inválida", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("AGREGAR A FIREBASE")
                        }
                    }
                }
            }

            // --- LISTA DE RESIDENTES ---
            item {
                Text("Listado Oficial", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=8.dp))
            }

            items(listaResidentes) { residente ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E))) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(residente.nombre, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Patente: ${residente.patente}", color = Color(0xFFF1C40F), fontWeight = FontWeight.Bold)
                            Text("Dpto: ${residente.dpto}", color = Color.LightGray, fontSize = 14.sp)
                        }
                        IconButton(onClick = {
                            ResidentesProvider.eliminarResidente(residente.id)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}