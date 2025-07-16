package com.example.appcertifyme.data

import com.example.appcertifyme.model.TipoConta
import com.example.appcertifyme.network.LoginRequest
import com.example.appcertifyme.network.RetrofitClient
import org.json.JSONObject

data class RespostaLogin(
    val sucesso: Boolean,
    val mensagem: String,
    val tipo: TipoConta?
)

object LoginProvider {
    suspend fun loginUsuario(email: String, senha: String): RespostaLogin {
        return try {
            val response = RetrofitClient.loginService.login(LoginRequest(email, senha))

            if (response.isSuccessful) {
                val resposta = response.body()
                val tipoConta = when (resposta?.usuario?.tipo?.uppercase()) {
                    "ESTUDANTE" -> TipoConta.ESTUDANTE
                    "ORGANIZADOR" -> TipoConta.ORGANIZADOR
                    else -> null
                }

                RespostaLogin(true, resposta?.mensagem ?: "", tipoConta)
            } else {
                val erroJson = response.errorBody()?.string()
                val mensagemErro = JSONObject(erroJson).optString("mensagem", "Erro desconhecido")

                RespostaLogin(false, mensagemErro, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            RespostaLogin(false, "Erro na conexão: ${e.localizedMessage}", null)
        }
    }
}
