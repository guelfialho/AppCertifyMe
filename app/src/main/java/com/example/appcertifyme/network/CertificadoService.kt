package com.example.appcertifyme.network

import com.example.appcertifyme.model.GetCertificadosResponse
import retrofit2.Response
import retrofit2.http.GET

interface CertificadosService {
    @GET("/certificados")
    suspend fun listarCertificadosDoEstudante(): Response<GetCertificadosResponse>
}
