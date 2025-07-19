package com.example.appcertifyme.model

data class ListarEventosOrganizadorResponse(
    val sucesso: Boolean,
    val eventos: List<Evento>
)