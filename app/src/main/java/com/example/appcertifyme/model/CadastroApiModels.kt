package com.example.appcertifyme.model

data class CadastroRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val tipo: String
)

data class CadastroResponse(
    val sucesso: Boolean,
    val mensagem: String,
    val usuario: UsuarioResponse? = null
)

data class UsuarioResponse(
    val id: String,
    val nome: String,
    val email: String,
    val tipo: String
)