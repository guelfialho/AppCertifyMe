package com.example.appcertifyme.data

import com.example.appcertifyme.model.Evento
import kotlinx.coroutines.delay
import java.util.UUID

object EventoProvider {
    private val todosEventos = mutableListOf(
        Evento("1", "Semana de Computação", "Palestras e oficinas", "2025-08-12", "Organizador UFBA"),
        Evento("2", "Workshop Kotlin", "Introdução ao Android", "2025-09-01", "Organizador UFBA")
    )

    suspend fun listarTodosEventos(): List<Evento> {
        delay(500)
        return todosEventos
    }

    suspend fun listarEventosCriados(organizador: String): List<Evento> {
        delay(500)
        return todosEventos.filter { it.organizador == organizador }
    }

    suspend fun criarEvento(titulo: String, descricao: String, data: String, organizador: String): Boolean {
        delay(500)
        val novoEvento = Evento(
            id = UUID.randomUUID().toString(),
            titulo = titulo,
            descricao = descricao,
            data = data,
            organizador = organizador
        )
        todosEventos.add(novoEvento)
        return true
    }

    suspend fun confirmarPresenca(eventoId: String, uuidEstudante: String): Boolean {
        delay(500)
        return true
    }
}