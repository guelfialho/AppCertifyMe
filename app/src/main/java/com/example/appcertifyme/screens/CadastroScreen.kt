package com.example.appcertifyme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcertifyme.model.TipoConta
import com.example.appcertifyme.data.CadastroProvider
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Cadastro", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            OutlinedButton(
                onClick = { dropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(tipoConta.label)
            }

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
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
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text("Cadastrar")
            }
        }
    }
}
