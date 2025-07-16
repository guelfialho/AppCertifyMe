package com.example.appcertifyme.network

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    val sucesso: Boolean,
    val mensagem: String,
    val token: String?,
    val usuario: UsuarioResponse?
)

data class UsuarioResponse(
    val nome: String,
    val tipo: String,
    val id: String
)