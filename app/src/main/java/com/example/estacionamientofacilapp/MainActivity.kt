package com.example.estacionamientofacilapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.estacionamientofacilapp.ui.theme.EstacionamientoFacilAppTheme

// ---------------------------
// MODELO DE USUARIOS
// ---------------------------
data class Usuario(val username: String, val password: String)

// Array estático para almacenar hasta 5 usuarios
// Array estático con 5 usuarios de prueba
val usuarios = mutableStateListOf(
    Usuario("test1", "1234"),
    Usuario("test2", "abcd"),
    Usuario("test3", "pass1"),
    Usuario("test4", "clave"),
    Usuario("test5", "qwerty")
)

// ---------------------------
// MAIN ACTIVITY
// ---------------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstacionamientoFacilAppTheme {
                AppNavigation()
            }
        }
    }
}

// ---------------------------
// NAVEGACIÓN
// ---------------------------
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("recover") { RecoverPasswordScreen(navController) }
    }
}

// ---------------------------
// LOGIN
// ---------------------------
@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xFF1565C0) // azul de fondo
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la app
            Text(
                text = "EstacionaFacil APP",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen
            Image(
                painter = painterResource(id = R.drawable.parking_sign_flat_style),
                contentDescription = "Icono principal",
                modifier = Modifier
                    .size(240.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            // Campo Usuario
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 38.sp // tamaño más grande
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF1E88E5), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

// Campo Contraseña
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 38.sp // tamaño más grande
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF1E88E5), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Botón Iniciar sesión
            Button(
                onClick = {
                    val user = usuarios.find { it.username == username && it.password == password }
                    errorMessage = if (user != null) "Bienvenido ${user.username}" else "Usuario o contraseña incorrectos"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión", fontSize = 22.sp) // letra más grande
            }

            Spacer(Modifier.height(8.dp))

// Enlaces de texto
            TextButton(onClick = { navController.navigate("register") }) {
                Text("¿No tienes cuenta? Regístrate", fontSize = 20.sp, color = Color.White)
            }

            TextButton(onClick = { navController.navigate("recover") }) {
                Text("¿Olvidaste tu contraseña?", fontSize = 20.sp, color = Color.White)
            }

// Mensaje de error
            if (errorMessage.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(errorMessage, color = Color.Yellow, fontSize = 20.sp) // más grande también
            }
        }
    }
}

// ---------------------------
// REGISTRO
// ---------------------------
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Usuario", fontSize = 32.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nuevo usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    usuarios.add(Usuario(username, password))
                    message = "Usuario registrado correctamente."
                    username = ""
                    password = ""

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar", fontSize = 22.sp)
            }

            TextButton(onClick = { navController.navigate("login") }) {
                Text("Volver al Login", fontSize = 22.sp)
            }

            if (message.isNotEmpty()) {
                Text(message, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

// ---------------------------
// RECUPERAR CONTRASEÑA
// ---------------------------
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RecoverPasswordScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar Contraseña", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val user = usuarios.find { it.username == username }
                    message = if (user != null) {
                        "Tu contraseña es: ${user.password}"
                    } else {
                        "Usuario no encontrado"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Recuperar")
            }

            TextButton(onClick = { navController.navigate("login") }) {
                Text("Volver al Login")
            }

            if (message.isNotEmpty()) {
                Text(message, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
