package com.example.appcertifyme.model


data class EventoCertificado(
    val id: String,
    val titulo: String,
    val data: String,
    val organizador: String
)

data class GetCertificados(
    val evento: EventoCertificado,
    val presencaConfirmada: Boolean,
    val emitido: Boolean
)


data class GetCertificadosResponse(
    val sucesso: Boolean,
    val certificados: List<GetCertificados>
)