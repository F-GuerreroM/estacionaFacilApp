package com.example.estacionamientofacilapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.estacionamientofacilapp.ui.screens.PlaceholderScreen
import com.example.estacionamientofacilapp.ui.screens.LoginScreen
import com.example.estacionamientofacilapp.ui.screens.RegisterScreen
import com.example.estacionamientofacilapp.ui.screens.RecoverPasswordScreen
import com.example.estacionamientofacilapp.ui.screens.ParkingListScreen
import com.example.estacionamientofacilapp.ui.screens.DashboardScreen
import com.example.estacionamientofacilapp.ui.screens.ResidentesScreen
import com.example.estacionamientofacilapp.ui.screens.UsuariosScreen
import com.example.estacionamientofacilapp.ui.screens.VehiculosEspecialesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(navController) }

        composable("dashboard") { DashboardScreen(navController) }

        composable("parking_list") { ParkingListScreen(navController) }

        composable("register") { RegisterScreen(navController) }

        composable("recover") { RecoverPasswordScreen(navController) }

        composable(
            route = "placeholder/{titulo}",
            arguments = listOf(navArgument("titulo") { type = NavType.StringType })
        ) { backStackEntry ->
            val tituloRecibido = backStackEntry.arguments?.getString("titulo") ?: "Sección"
            PlaceholderScreen(navController, tituloRecibido)
        }

        composable("usuarios_screen") { UsuariosScreen(navController) }
        composable("residentes_screen") { ResidentesScreen(navController) }
        composable("vehiculosEspeciales_screen") { VehiculosEspecialesScreen(navController) }
    }
}