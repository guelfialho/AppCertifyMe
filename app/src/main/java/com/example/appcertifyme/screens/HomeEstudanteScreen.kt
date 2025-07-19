package com.example.appcertifyme.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcertifyme.data.CertificadoProvider
import com.example.appcertifyme.data.EventoProvider
import com.example.appcertifyme.model.Certificado
import com.example.appcertifyme.model.Evento
import com.example.appcertifyme.utils.gerarQrCode
import kotlinx.coroutines.launch
import com.example.appcertifyme.components.HeaderUsuario

@Composable
fun HomeEstudanteScreen(
    navController: NavController,
    nome: String,
    id: String
) {
    val tabs = listOf("Eventos", "Certificados", "QrCode")
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderUsuario(nomeUsuario = nome)

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> AbaEventos()
            1 -> AbaCertificados()
            2 -> AbaQrCode(id)
        }
    }
}

@Composable
fun AbaEventos() {
    val scope = rememberCoroutineScope()
    var eventos by remember { mutableStateOf<List<Evento>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            eventos = EventoProvider.listarTodosEventos()
        }
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(eventos) { evento ->
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = evento.titulo, style = MaterialTheme.typography.titleMedium)
                    Text(text = evento.descricao)
                    Text(text = "Data: ${evento.data}")
                    Text(text = "Organizador: ${evento.organizador}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        scope.launch {
                            val sucesso = EventoProvider.inscreverNoEvento(evento.id)
                            if (sucesso) {
                                Toast.makeText(context, "Inscrição realizada com sucesso!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Erro ao se inscrever.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                        Text("Inscrever-se")
                    }
                }
            }
        }
    }
}

@Composable
fun AbaCertificados() {
    val scope = rememberCoroutineScope()
    var certificados by remember { mutableStateOf<List<Certificado>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                certificados = CertificadoProvider.listarCertificados()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Erro ao carregar certificados.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(certificados) { cert ->
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = cert.evento.titulo, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Data: ${cert.evento.data}")
                    Text(text = "Organizador: ${cert.evento.organizador}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                val sucesso = CertificadoProvider.baixarCertificado(cert.evento.id)
                                Toast.makeText(
                                    context,
                                    if (sucesso) "Certificado salvo com sucesso!" else "Erro ao baixar.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        enabled = cert.presencaConfirmada
                    ) {
                        Text(if (cert.presencaConfirmada) "Baixar Certificado" else "Aguardando Confirmação")
                    }
                }
            }
        }
    }
}


@Composable
fun AbaQrCode(id: String) {
    val qr = remember { gerarQrCode(id) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text("Seu QR Code", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Image(bitmap = qr, contentDescription = "QR Code")
    }
}
