package com.example.appcertifyme.network

import com.example.appcertifyme.data.CriarEventoRequest
import com.example.appcertifyme.data.CriarEventoResponse
import com.example.appcertifyme.model.Evento
import com.example.appcertifyme.model.ListarEventosOrganizadorResponse
import com.example.appcertifyme.model.MensagemResponse
import com.example.appcertifyme.models.InscricaoRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventoService {
    @GET("/eventos")
    suspend fun listarTodos(): List<Evento>

    @POST("/eventos")
    suspend fun criarEvento(@Body request: CriarEventoRequest): CriarEventoResponse

    @GET("/eventos/organizador")
    suspend fun listarEventosOrganizador(): ListarEventosOrganizadorResponse

    @POST("/eventos/inscricao")
    suspend fun inscrever(@Body request: InscricaoRequest): MensagemResponse
}