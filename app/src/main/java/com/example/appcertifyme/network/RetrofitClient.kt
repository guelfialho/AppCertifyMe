package com.example.appcertifyme.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofitNoAuth = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val retrofitWithAuth = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3000/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    )
    .build()

object RetrofitClient {
    val loginService: LoginService by lazy {
        retrofitNoAuth.create(LoginService::class.java)
    }

    val certificadosService: CertificadosService by lazy {
        retrofitWithAuth.create(CertificadosService::class.java)
    }
}
