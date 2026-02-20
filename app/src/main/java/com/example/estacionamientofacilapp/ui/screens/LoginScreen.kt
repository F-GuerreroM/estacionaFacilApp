package com.example.estacionamientofacilapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.estacionamientofacilapp.R
import com.example.estacionamientofacilapp.data.UsuariosProvider
import android.net.Uri

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {

            val uri = Uri.parse("content://com.example.estacionamientofacilapp.provider")
            val cursor = context.contentResolver.query(uri, null, null, null, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val mensaje = it.getString(0)
                    println("ESTACIONAFACIL PROVIDER TEST: $mensaje")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Colores del tema oscuro
    val colorFondo = Color(0xFF2C3E50)
    val colorAcento = Color(0xFFF1C40F)
    val colorInputFondo = Color(0xFF34495E)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "EstacionaFácil",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.parking_sign_flat_style),
                contentDescription = "Logo Estacionamiento",
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario", fontSize = 18.sp) },
                    textStyle = TextStyle(fontSize = 22.sp, color = Color.White),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorInputFondo,
                        unfocusedContainerColor = colorInputFondo,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = colorAcento,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = colorAcento,
                        unfocusedLabelColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña", fontSize = 18.sp) },
                    textStyle = TextStyle(fontSize = 22.sp, color = Color.White),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorInputFondo,
                        unfocusedContainerColor = colorInputFondo,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = colorAcento,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = colorAcento,
                        unfocusedLabelColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()) {

                            UsuariosProvider.login(username, password)
                                .addOnSuccessListener {
                                    errorMessage = ""
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    errorMessage = "Error: ${exception.localizedMessage}"
                                }

                        } else {
                            errorMessage = "Complete todos los campos"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorAcento)
                ) {
                    Text("INGRESAR", fontSize = 22.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? CREAR", color = colorAcento, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = { navController.navigate("recover") }) {
            Text("Olvidé mi contraseña", color = Color.LightGray, fontSize = 18.sp)
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(errorMessage, color = Color(0xFFE74C3C), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}