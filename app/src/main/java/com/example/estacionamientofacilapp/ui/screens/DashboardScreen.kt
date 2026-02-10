package com.example.estacionamientofacilapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class MenuItem(val titulo: String, val icono: ImageVector, val ruta: String)

@Composable
fun DashboardScreen(navController: NavController) {
    val context = LocalContext.current

    // Lista de Opciones del Menú
    val menuItems = listOf(
        MenuItem("Entradas / Salidas", Icons.Default.List, "parking_list"),
        MenuItem("Residentes", Icons.Default.Person, "placeholder/Residentes"),
        MenuItem("Vehículos Especiales", Icons.Default.Star, "placeholder/Especiales"),
        MenuItem("Usuarios APP", Icons.Default.Phone, "placeholder/Usuarios"),
        MenuItem("Horarios", Icons.Default.DateRange, "placeholder/Horarios"),
        MenuItem("Tarifas", Icons.Default.ShoppingCart, "placeholder/Tarifas")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E50)) // Fondo oscuro
            .padding(16.dp)
    ) {
        Text(
            text = "Menú Principal",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp, top = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(menuItems) { item ->
                MenuCard(item) {
                    if (item.ruta == "parking_list") {
                        navController.navigate("parking_list")
                    } else {
                        navController.navigate(item.ruta)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuCard(item: MenuItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF34495E)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icono,
                contentDescription = item.titulo,
                tint = Color(0xFFF1C40F),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = item.titulo,
                color = Color.White,
                fontSize = 20.sp, // Letra Grande
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}