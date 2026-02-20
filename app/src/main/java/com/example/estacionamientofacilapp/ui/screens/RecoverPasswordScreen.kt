package com.example.estacionamientofacilapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
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
fun RecoverPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var esError by remember { mutableStateOf(false) }
    var cargando by remember { mutableStateOf(false) }

    // Colores del tema
    val colorFondo = Color(0xFF2C3E50)
    val colorCard = Color(0xFF34495E)
    val colorAcento = Color(0xFFF1C40F) // Amarillo/Dorado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recuperar Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Ingresa tu correo para recibir un enlace de restablecimiento.",
            color = Color.LightGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = colorCard),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = colorAcento) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = colorAcento,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = colorAcento,
                        unfocusedLabelColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (email.isNotEmpty()) {
                            cargando = true
                            mensaje = ""
                            // LLAMADA A FIREBASE
                            UsuariosProvider.recuperarClave(email)
                                .addOnSuccessListener {
                                    cargando = false
                                    esError = false
                                    mensaje = "¡Listo! Revisa tu bandeja de entrada (y spam)."
                                }
                                .addOnFailureListener {
                                    cargando = false
                                    esError = true
                                    mensaje = "Error: ${it.localizedMessage}"
                                }
                        } else {
                            esError = true
                            mensaje = "Por favor ingresa un correo."
                        }
                    },
                    enabled = !cargando,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorAcento),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (cargando) {
                        CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                    } else {
                        Text("ENVIAR CORREO", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (mensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mensaje,
                color = if (esError) Color(0xFFE74C3C) else Color(0xFF27AE60), // Rojo o Verde
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = colorAcento)
            Spacer(Modifier.width(8.dp))
            Text("Volver al Login", color = colorAcento, fontSize = 16.sp)
        }
    }
}