package com.example.appcertifyme.data

import com.example.appcertifyme.model.Evento
import kotlinx.coroutines.delay

object EventoProvider {
    suspend fun listarEventos(): List<Evento> {
        delay(500)
        return listOf(
            Evento("1", "Semana de Computação", "Palestras e oficinas", "2025-08-12", "DCC - UFBA"),
            Evento("2", "Workshop Kotlin", "Introdução ao Android", "2025-09-01", "PET Computação")
        )
    }
}
