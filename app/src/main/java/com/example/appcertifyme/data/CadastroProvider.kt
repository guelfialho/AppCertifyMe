package com.example.appcertifyme.data

import kotlinx.coroutines.delay

data class RespostaCadastro(val sucesso: Boolean, val mensagem: String)

object CadastroProvider {
    suspend fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        tipo: String
    ): RespostaCadastro {
        delay(1500)


        return RespostaCadastro(
            sucesso = true,
            mensagem = "Cadastro realizado com sucesso"
        )
    }
}
