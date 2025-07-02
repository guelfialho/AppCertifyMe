package com.example.appcertifyme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcertifyme.data.CadastroProvider
import com.example.appcertifyme.model.TipoConta
import kotlinx.coroutines.launch

@Composable
fun CadastroScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var tipoConta by remember { mutableStateOf(TipoConta.ESTUDANTE) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crie sua conta",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome completo") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Dropdown simples sem usar API experimental
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { dropdownExpanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = tipoConta.label)
                        }

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TipoConta.values().forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo.label) },
                                    onClick = {
                                        tipoConta = tipo
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (nome.isNotBlank() && email.isNotBlank() && senha.isNotBlank()) {
                                coroutineScope.launch {
                                    loading = true
                                    val resposta = CadastroProvider.cadastrarUsuario(
                                        nome = nome,
                                        email = email,
                                        senha = senha,
                                        tipo = tipoConta.name
                                    )
                                    loading = false

                                    Toast.makeText(context, resposta.mensagem, Toast.LENGTH_SHORT).show()

                                    if (resposta.sucesso) {
                                        navController.navigate("login")
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = !loading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Cadastrar")
                        }
                    }

                    TextButton(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Já tem uma conta? Faça login")
                    }
                }
            }
        }
    }
}
