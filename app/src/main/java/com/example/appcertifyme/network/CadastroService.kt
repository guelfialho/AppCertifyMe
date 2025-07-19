package com.example.appcertifyme.network

import com.example.appcertifyme.model.CadastroRequest
import com.example.appcertifyme.model.CadastroResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CadastroService {
    @POST("/usuarios/cadastro")
    suspend fun cadastrarUsuario(@Body body: CadastroRequest): CadastroResponse
}