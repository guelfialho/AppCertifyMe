package com.example.appcertifyme.data

import com.example.appcertifyme.model.TipoConta
import kotlinx.coroutines.delay

data class RespostaLogin(
    val sucesso: Boolean,
    val mensagem: String,
    val tipo: TipoConta?
)

object LoginProvider {
    suspend fun loginUsuario(
        email: String,
        senha: String
    ): RespostaLogin {
        delay(1000)

        return when {
            email == "teste@ufba.br" && senha == "1234" -> {
                RespostaLogin(true, "Login bem-sucedido!", TipoConta.ESTUDANTE)
            }

            email == "org@ufba.br" && senha == "1234" -> {
                RespostaLogin(true, "Login bem-sucedido!", TipoConta.ORGANIZADOR)
            }

            else -> {
                RespostaLogin(false, "Email ou senha inválidos.", null)
            }
        }
    }
}
