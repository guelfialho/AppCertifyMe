package com.example.appcertifyme.model

data class User(
    val nome: String,
    val email: String,
    val senha: String,
    val tipo: TipoConta,
    val uuid: String = java.util.UUID.randomUUID().toString()
)
