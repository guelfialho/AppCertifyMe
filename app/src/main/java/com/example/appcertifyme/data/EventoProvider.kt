package com.example.appcertifyme.data

import com.example.appcertifyme.model.Evento
import com.example.appcertifyme.models.InscricaoRequest
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

    suspend fun inscreverNoEvento(eventoId: String): Boolean {
        return try {
            val resposta = RetrofitClient.eventoService.inscrever(
                InscricaoRequest(eventoId)
            )
            resposta.sucesso
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    suspend fun listarEventosOrganizador(organizador: String): List<Evento> {
        return try {

            val response = RetrofitClient.eventoService.listarEventosOrganizador()
            if (response.sucesso) response.eventos else emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun criarEvento(titulo: String, descricao: String, data: String): Boolean {
        return try {
            val request = CriarEventoRequest(
                titulo = titulo,
                descricao = descricao,
                data = data
            )
            val response = RetrofitClient.eventoService.criarEvento(request)
            response.sucesso
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun confirmarPresenca(eventoId: String, uuidEstudante: String): Boolean {
        return false
    }
}
