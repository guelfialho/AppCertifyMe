package com.example.appcertifyme.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.example.appcertifyme.data.PresencaProvider
import com.example.appcertifyme.model.Evento
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.launch
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeOrganizadorScreen(
    navController: NavController, nome: String, id: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var eventos by remember { mutableStateOf<List<Evento>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    var eventoSelecionado by remember { mutableStateOf<Evento?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            if (result.contents != null && eventoSelecionado != null) {
                val uuidEstudante = result.contents // Este é o ID do estudante lido do QR Code
                val eventoId = eventoSelecionado!!.id // Este é o ID do evento selecionado

                scope.launch {
                    // Chamar o novo PresencaProvider
                    val sucesso = PresencaProvider.confirmarPresenca(uuidEstudante, eventoId)
                    Toast.makeText(
                        context,
                        if (sucesso) "Presença confirmada!" else "Erro ao confirmar ou presença já confirmada.",
                        Toast.LENGTH_LONG // Use LONG para visibilidade
                    ).show()
                    eventoSelecionado = null // Limpar o evento selecionado
                }
            } else {
                Toast.makeText(context, "Leitura cancelada ou evento não selecionado.", Toast.LENGTH_SHORT).show()
                eventoSelecionado = null
            }
        }
    )

    LaunchedEffect(Unit) {
        scope.launch {
            eventos = EventoProvider.listarEventosOrganizador(id)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderUsuario(nomeUsuario = nome)

        Spacer(modifier = Modifier.height(12.dp))

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
                            eventoSelecionado = evento
                            launcher.launch(
                                ScanOptions().apply {
                                    setPrompt("Aponte para o QR do estudante")
                                    setBeepEnabled(false)
                                    setOrientationLocked(true)
                                }
                            )
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
                    val criado = EventoProvider.criarEvento(titulo, descricao, data)
                    if (criado) {
                        eventos = EventoProvider.listarEventosOrganizador(id)
                        Toast.makeText(context, "Evento criado com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Erro ao criar evento.", Toast.LENGTH_SHORT).show()
                    }
                    showDialog = false
                }
            }
        )
    }
}
// Mantenha o DialogNovoEvento inalterado
@Composable
fun DialogNovoEvento(onDismiss: () -> Unit, onSalvar: (String, String, String) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val regex = Regex("""\d{4}-\d{2}-\d{2}""")
                if (titulo.isBlank() || data.isBlank()) {
                    Toast.makeText(context, "Título e Data são obrigatórios.", Toast.LENGTH_SHORT).show()
                } else if (!regex.matches(data)) {
                    Toast.makeText(context, "Data inválida. Use formato: yyyy-MM-dd", Toast.LENGTH_SHORT).show()
                } else {
                    onSalvar(titulo, descricao, data)
                }
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
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") }
                )
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") }
                )
                OutlinedTextField(
                    value = data,
                    onValueChange = { data = it },
                    label = { Text("Data (yyyy-MM-dd)") },
                    placeholder = { Text("ex: 2025-07-19") }
                )
            }
        }
    )
}