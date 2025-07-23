package com.example.appcertifyme.data

import com.example.appcertifyme.network.RetrofitClient
import java.lang.Exception

object PresencaProvider {

    suspend fun confirmarPresenca(estudanteId: String, eventoId: String): Boolean {
        return try {
            val request = ConfirmarPresencaRequest(eventoId = eventoId, estudanteId = estudanteId)
            val response = RetrofitClient.presencaService.confirmarPresenca(request)
          
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}