package com.example.appcertifyme.data

import com.example.appcertifyme.model.Certificado
import kotlinx.coroutines.delay

object CertificadoProvider {
    suspend fun listarCertificados(uuidEstudante: String): List<Certificado> {
        delay(500)
        val eventosInscritos = EventoProvider.listarTodosEventos().filter {
            uuidEstudante == "123e4567-e89b-12d3-a456-426614174000" && (it.id == "1" || it.id == "2")
        }
        return listOf(
            Certificado(eventosInscritos[0], presencaConfirmada = true),
            Certificado(eventosInscritos[1], presencaConfirmada = false)
        )
    }

    suspend fun baixarCertificado(eventoId: String): Boolean {
        delay(1000)
        return true
    }
}