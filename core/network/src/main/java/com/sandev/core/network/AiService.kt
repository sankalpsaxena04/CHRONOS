package com.sandev.core.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AiService {
    @GET("/prompt/{prompt}")
    suspend fun getGreetings(@Path("prompt")prompt: String): Response<String>
}