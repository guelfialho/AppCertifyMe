package com.example.appcertifyme.data

import com.example.appcertifyme.model.Certificado
import com.example.appcertifyme.model.Evento
import kotlinx.coroutines.delay

object CertificadoProvider {
    suspend fun listarCertificados(): List<Certificado> {
        delay(500)
        val eventos = EventoProvider.listarEventos()
        return listOf(
            Certificado(eventos[0], presencaConfirmada = true),
            Certificado(eventos[1], presencaConfirmada = false)
        )
    }

    suspend fun baixarCertificado(eventoId: String): Boolean {
        delay(1000)
        // Simula chamada para download do certificado
        return true
    }
}
