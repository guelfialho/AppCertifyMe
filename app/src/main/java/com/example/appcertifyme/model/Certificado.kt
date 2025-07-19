package com.example.appcertifyme.model

data class Certificado(
    val evento: EventoCertificado,
    val presencaConfirmada: Boolean,
    val emitido: Boolean
)
