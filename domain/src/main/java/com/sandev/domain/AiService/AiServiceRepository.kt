package com.sandev.domain.AiService

interface AiServiceRepository {
    suspend fun getGreetings(prompt:String):Result<String>
}