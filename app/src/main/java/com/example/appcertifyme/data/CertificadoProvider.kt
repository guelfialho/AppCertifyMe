package com.example.appcertifyme.data

import com.example.appcertifyme.model.Certificado
import com.example.appcertifyme.network.RetrofitClient

object CertificadoProvider {

    suspend fun listarCertificados(): List<Certificado> {
        val response = RetrofitClient.certificadosService.listarCertificadosDoEstudante()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.sucesso) {
                // Converte List<GetCertificados> para List<Certificado>
                return body.certificados.map { getCert ->
                    Certificado(
                        evento = getCert.evento,
                        presencaConfirmada = getCert.presencaConfirmada,
                        emitido = getCert.emitido
                    )
                }
            } else {
                throw Exception(body?.toString() ?: "Resposta inválida")
            }
        } else {
            throw Exception("Erro na API: ${response.code()} ${response.message()}")
        }
    }

    suspend fun baixarCertificado(eventoId: String): Boolean {
        // Aqui pode implementar o download, por enquanto pode deixar mock
        return true
    }
}
