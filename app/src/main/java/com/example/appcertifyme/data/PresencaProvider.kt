package com.example.appcertifyme.data

import kotlinx.coroutines.delay

object PresencaProvider {
    suspend fun confirmarPresenca(uuidEstudante: String, eventoId: String): Boolean {
        delay(1000) // simula chamada real
        println("Mock: presença registrada -> UUID=$uuidEstudante | Evento=$eventoId")
        return true // sempre sucesso (mock)
    }
}
