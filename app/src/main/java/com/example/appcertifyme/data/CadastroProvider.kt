package com.example.appcertifyme.data

import com.example.appcertifyme.model.CadastroRequest
import com.example.appcertifyme.network.RetrofitClient

data class RespostaCadastro(val sucesso: Boolean, val mensagem: String)

object CadastroProvider {
    suspend fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        tipo: String
    ): RespostaCadastro {
        return try {
            val resposta = RetrofitClient.cadastroService.cadastrarUsuario(
                CadastroRequest(
                    nome = nome,
                    email = email,
                    senha = senha,
                    tipo = tipo
                )
            )

            RespostaCadastro(
                sucesso = resposta.sucesso,
                mensagem = resposta.mensagem
            )
        } catch (e: Exception) {
            e.printStackTrace()
            RespostaCadastro(
                sucesso = false,
                mensagem = "Erro ao conectar com o servidor: ${e.message}"
            )
        }
    }
}
