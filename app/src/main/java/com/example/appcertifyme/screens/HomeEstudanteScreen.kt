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

@Composable
fun HomeEstudanteScreen(
    navController: NavController,
    nomeUsuario: String = "Fulano UFBA",
    uuid: String = "123e4567-e89b-12d3-a456-426614174000"
) {
    val tabs = listOf("Eventos", "Certificados", "QrCode")
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Ícone do usuário",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Bem-vindo!", style = MaterialTheme.typography.bodyLarge)
                Text(text = nomeUsuario, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            }
        }

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
            1 -> AbaCertificados(uuid)
            2 -> AbaQrCode(uuid)
        }
    }
}

@Composable
fun AbaEventos() {
    val scope = rememberCoroutineScope()
    var eventos by remember { mutableStateOf<List<Evento>>(emptyList()) }

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
                    Button(onClick = { /* ação mockada de inscrição */ }) {
                        Text("Inscrever-se")
                    }
                }
            }
        }
    }
}

@Composable
fun AbaCertificados(uuid: String) {
    val scope = rememberCoroutineScope()
    var certificados by remember { mutableStateOf<List<Certificado>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            certificados = CertificadoProvider.listarCertificados(uuid)
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
fun AbaQrCode(uuid: String) {
    val qr = remember { gerarQrCode(uuid) }

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
