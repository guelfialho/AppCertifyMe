package com.example.appcertifyme.network

import com.example.appcertifyme.model.Evento
import com.example.appcertifyme.model.MensagemResponse
import com.example.appcertifyme.models.InscricaoRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventoService {
    @GET("/eventos")
    suspend fun listarTodos(): List<Evento>

    @POST("/eventos/inscricao")
    suspend fun inscrever(@Body request: InscricaoRequest): MensagemResponse
}