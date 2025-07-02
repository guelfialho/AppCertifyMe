package com.example.appcertifyme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcertifyme.components.HeaderUsuario
import com.example.appcertifyme.data.EventoProvider
import com.example.appcertifyme.model.Evento
import kotlinx.coroutines.launch

@Composable
fun HomeOrganizadorScreen(
    navController: NavController,
    nomeOrganizador: String = "Organizador UFBA"
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var eventos by remember { mutableStateOf<List<Evento>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            eventos = EventoProvider.listarEventosCriados(nomeOrganizador)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderUsuario(nomeUsuario = nomeOrganizador)

        Spacer(modifier = Modifier.height(12.dp))

        // Botão logo abaixo do header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { showDialog = true }) {
                Text("+ Novo Evento")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Seus Eventos",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(eventos) { evento ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = evento.titulo, style = MaterialTheme.typography.titleMedium)
                        Text(text = evento.descricao)
                        Text(text = "Data: ${evento.data}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            scope.launch {
                                val sucesso = EventoProvider.confirmarPresenca(evento.id, uuidEstudante = "uuid-mock")
                                Toast.makeText(
                                    context,
                                    if (sucesso) "Presença confirmada!" else "Erro ao confirmar.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text("Confirmar presença via QR")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        DialogNovoEvento(
            onDismiss = { showDialog = false },
            onSalvar = { titulo, descricao, data ->
                scope.launch {
                    val criado = EventoProvider.criarEvento(titulo, descricao, data, nomeOrganizador)
                    if (criado) {
                        eventos = EventoProvider.listarEventosCriados(nomeOrganizador)
                        Toast.makeText(context, "Evento criado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    showDialog = false
                }
            }
        )
    }
}

@Composable
fun DialogNovoEvento(onDismiss: () -> Unit, onSalvar: (String, String, String) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSalvar(titulo, descricao, data)
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Novo Evento") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
                OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") })
                OutlinedTextField(value = data, onValueChange = { data = it }, label = { Text("Data") })
            }
        }
    )
}
