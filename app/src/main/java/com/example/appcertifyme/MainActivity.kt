package com.example.appcertifyme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcertifyme.screens.CadastroScreen
import com.example.appcertifyme.screens.LoginScreen
import com.example.appcertifyme.ui.theme.AppCertifyMeTheme
import com.example.appcertifyme.screens.HomeEstudanteScreen
import com.example.appcertifyme.screens.HomeOrganizadorScreen
import com.example.appcertifyme.screens.TelaInicialScreen
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCertifyMeTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "telaInicial"
                    ) {
                        composable("telaInicial") {
                            TelaInicialScreen(navController)
                        }
                        composable("cadastro") {
                            CadastroScreen(navController)
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable(
                            "homeEstudante/{nome}/{id}",
                            arguments = listOf(
                                navArgument("nome") { type = NavType.StringType },
                                navArgument("id") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val nome = backStackEntry.arguments?.getString("nome") ?: "Sem Nome"
                            val id = backStackEntry.arguments?.getString("id") ?: "Sem UUID"
                            HomeEstudanteScreen(navController, nome, id)
                        }
                        composable("homeOrganizador") {
                            HomeOrganizadorScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
