package com.example.appcertifyme.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface LoginService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
