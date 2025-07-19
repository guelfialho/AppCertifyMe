package com.example.appcertifyme.network

import com.example.appcertifyme.model.Evento
import retrofit2.http.GET

interface EventoService {
    @GET("/eventos")
    suspend fun listarTodos(): List<Evento>
}