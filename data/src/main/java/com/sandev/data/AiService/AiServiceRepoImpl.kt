package com.sandev.data.AiService

import com.sandev.core.network.AiService
import com.sandev.domain.AiService.AiServiceRepository
import javax.inject.Inject

class AiServiceRepoImpl @Inject constructor(private val aiService: AiService): AiServiceRepository {
    override suspend fun getGreetings(prompt: String): Result<String> {
        return try {
            val response = aiService.getGreetings(prompt)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                }?: run {
                    Result.failure(Exception("error"))
                }
            } else {
                Result.failure(Exception("Error"))
            }

        }catch (e: Exception){
            Result.failure(e)
        }
    }
}