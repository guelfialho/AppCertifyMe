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
import androidx.compose.ui.unit.dp

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
                        startDestination = "cadastro"
                    ) {
                        composable("cadastro") {
                            CadastroScreen(navController)
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("homeEstudante") {
                            HomeEstudanteScreen(navController)
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
