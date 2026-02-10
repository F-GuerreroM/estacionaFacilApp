package com.example.estacionamientofacilapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
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
import com.example.estacionamientofacilapp.data.ClienteParking
import com.example.estacionamientofacilapp.data.listaClientes
import com.example.estacionamientofacilapp.utils.formatearPatente
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// DATOS DE PRUEBA PARA RESIDENTES
data class ResidenteMock(val nombre: String, val patente: String, val dpto: String)
val listaResidentes = listOf(
    ResidenteMock("Juan Pérez", "AA1122", "Dpto 101"),
    ResidenteMock("Maria Soto", "BB3344", "Dpto 205"),
    ResidenteMock("Pedro Diaz", "CC5566", "Dpto 303"),
    ResidenteMock("Ana Torres", "DD7788", "Dpto 410")
)

enum class TipoDialogo { NINGUNO, SELECCION, LISTA_RESIDENTES, FORMULARIO_VISITA }

@Composable
fun ParkingListScreen(navController: NavController) {
    val context = LocalContext.current

    var textoBusqueda by remember { mutableStateOf("") }
    var updateTrigger by remember { mutableStateOf(false) }
    var dialogoActual by remember { mutableStateOf(TipoDialogo.NINGUNO) }

    // VARIABLES FORMULARIO VISITA
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevaPatente by remember { mutableStateOf("") }
    var nuevoMotivo by remember { mutableStateOf("") } // CAMPO NUEVO

    val listaFiltrada = remember(textoBusqueda, listaClientes.size, updateTrigger) {
        listaClientes.filter { cliente ->
            cliente.patente.contains(textoBusqueda, ignoreCase = true) ||
                    cliente.nombre.contains(textoBusqueda, ignoreCase = true)
        }
    }

    if (dialogoActual == TipoDialogo.SELECCION) {
        AlertDialog(
            onDismissRequest = { dialogoActual = TipoDialogo.NINGUNO },
            containerColor = Color(0xFF34495E),
            title = { Text("Registrar Ingreso", color = Color.White, fontSize = 24.sp) },
            text = { Text("¿Qué tipo de vehículo ingresa?", color = Color.LightGray, fontSize = 18.sp) },
            confirmButton = {
                Button(
                    onClick = { dialogoActual = TipoDialogo.LISTA_RESIDENTES },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60))
                ) {
                    Text("RESIDENTE", fontSize = 18.sp)
                }
            },
            dismissButton = {
                Button(
                    onClick = { dialogoActual = TipoDialogo.FORMULARIO_VISITA },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22))
                ) {
                    Text("VISITA / EXTERNO", fontSize = 18.sp)
                }
            }
        )
    }

    if (dialogoActual == TipoDialogo.FORMULARIO_VISITA) {
        AlertDialog(
            onDismissRequest = { dialogoActual = TipoDialogo.NINGUNO },
            containerColor = Color(0xFF2C3E50),
            title = { Text("Datos Visita", color = Color.White, fontSize = 24.sp) },
            text = {
                Column {
                    OutlinedTextField(
                        value = nuevoNombre,
                        onValueChange = { nuevoNombre = it },
                        label = { Text("Nombre Conductor") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF34495E),
                            unfocusedContainerColor = Color(0xFF34495E),
                            focusedBorderColor = Color(0xFFF1C40F),
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color(0xFFF1C40F),
                            unfocusedLabelColor = Color.White
                        )
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nuevaPatente,
                        onValueChange = { nuevaPatente = it },
                        label = { Text("Patente") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF34495E),
                            unfocusedContainerColor = Color(0xFF34495E),
                            focusedBorderColor = Color(0xFFF1C40F),
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color(0xFFF1C40F),
                            unfocusedLabelColor = Color.White
                        )
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nuevoMotivo,
                        onValueChange = { nuevoMotivo = it },
                        label = { Text("Motivo de Visita") },
                        placeholder = { Text("Ej: Entrevista, Delivery...", color = Color.LightGray) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF34495E),
                            unfocusedContainerColor = Color(0xFF34495E),
                            focusedBorderColor = Color(0xFFF1C40F),
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color(0xFFF1C40F),
                            unfocusedLabelColor = Color.White
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(nuevoNombre.isNotEmpty() && nuevaPatente.isNotEmpty()) {
                            val hora = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                            val nombreCompleto = if (nuevoMotivo.isNotEmpty()) "$nuevoNombre ($nuevoMotivo)" else nuevoNombre

                            listaClientes.add(0, ClienteParking(nombreCompleto, nuevaPatente, hora, false))
                            updateTrigger = !updateTrigger
                            // Limpiar campos
                            nuevoNombre = ""
                            nuevaPatente = ""
                            nuevoMotivo = ""
                            dialogoActual = TipoDialogo.NINGUNO
                            Toast.makeText(context, "Visita registrada", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1C40F))
                ) {
                    Text("GUARDAR", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (dialogoActual == TipoDialogo.LISTA_RESIDENTES) {
        AlertDialog(
            onDismissRequest = { dialogoActual = TipoDialogo.NINGUNO },
            containerColor = Color(0xFF2C3E50),
            title = { Text("Seleccionar Residente", color = Color.White, fontSize = 24.sp) },
            text = {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(listaResidentes) { residente ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    val hora = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                                    listaClientes.add(0, ClienteParking("${residente.nombre} (${residente.dpto})", residente.patente, hora, true))
                                    updateTrigger = !updateTrigger
                                    dialogoActual = TipoDialogo.NINGUNO
                                    Toast.makeText(context, "Residente Ingresado", Toast.LENGTH_SHORT).show()
                                },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(residente.nombre, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text(residente.patente, color = Color(0xFFF1C40F), fontSize = 16.sp)
                                    Text(residente.dpto, color = Color.LightGray, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { dialogoActual = TipoDialogo.NINGUNO }) {
                    Text("CANCELAR", color = Color(0xFFE74C3C), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color(0xFF2C3E50))
                    .padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { dialogoActual = TipoDialogo.SELECCION },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(55.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("INGRESO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { Toast.makeText(context, "Seleccione un auto de la lista para dar salida", Toast.LENGTH_LONG).show() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0392B)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(55.dp)
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("SALIDA", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1C40F)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                    Spacer(Modifier.width(8.dp))
                    Text("VOLVER AL MENÚ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        },
        containerColor = Color(0xFF2C3E50)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Control de Acceso", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text("Buscar patente...", fontSize = 18.sp) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF1C40F),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFFF1C40F),
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color(0xFFF1C40F)
                )
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 16.dp)) {
                items(listaFiltrada) { cliente ->
                    ParkingCard(cliente)
                }
            }
        }
    }
}

@Composable
fun ParkingCard(cliente: ClienteParking) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cliente.nombre, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                var textoMostrado: String
                var colorTexto: Color
                try {
                    textoMostrado = "Patente: ${cliente.patente.formatearPatente()}"
                    colorTexto = Color(0xFFF1C40F)
                } catch (e: Exception) {
                    textoMostrado = "Error formato"
                    colorTexto = Color.Red
                }
                Text(text = textoMostrado, color = colorTexto, fontWeight = FontWeight.Black, fontSize = 20.sp)
                Text(text = "Entrada: ${cliente.horaEntrada}", color = Color.LightGray, fontSize = 16.sp)
            }
            Surface(
                color = if(cliente.isMensual) Color(0xFF27AE60) else Color(0xFFE74C3C),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = if(cliente.isMensual) "M" else "V", color = Color.White, modifier = Modifier.padding(16.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}