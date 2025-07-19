package com.example.appcertifyme.data

import com.example.appcertifyme.model.Evento

data class CriarEventoRequest(
    val titulo: String,
    val descricao: String,
    val data: String
)

data class CriarEventoResponse(
    val sucesso: Boolean,
    val mensagem: String,
    val evento: Evento?
)