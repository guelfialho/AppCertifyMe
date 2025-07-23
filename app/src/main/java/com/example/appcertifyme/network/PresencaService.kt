package com.example.appcertifyme.network

import com.example.appcertifyme.data.ConfirmarPresencaRequest
import com.example.appcertifyme.model.MensagemResponse // Reutilizando MensagemResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PresencaService {
    @POST("/presencas") // Este é o endpoint que você configurou no Express
    suspend fun confirmarPresenca(@Body request: ConfirmarPresencaRequest): MensagemResponse
}