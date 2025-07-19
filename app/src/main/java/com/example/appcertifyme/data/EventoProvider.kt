package com.example.appcertifyme.data

import com.example.appcertifyme.model.Evento
import com.example.appcertifyme.network.RetrofitClient

object EventoProvider {

    suspend fun listarTodosEventos(): List<Evento> {
        return try {
            RetrofitClient.eventoService.listarTodos()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun listarEventosCriados(organizador: String): List<Evento> {
        return emptyList()
    }

    suspend fun criarEvento(titulo: String, descricao: String, data: String, organizador: String): Boolean {
        return false
    }

    suspend fun confirmarPresenca(eventoId: String, uuidEstudante: String): Boolean {
        return false
    }
}
