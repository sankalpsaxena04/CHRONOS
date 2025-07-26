package com.sandev.domain.AiService

import com.sandev.core.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AiServiceUseCase @Inject constructor(private val aiServiceRepository: AiServiceRepository) {
    operator fun invoke(prompt:String): Flow<Result<String>>  = flow {
        emit(Result.Loading)
        val response = aiServiceRepository.getGreetings(prompt)
        if(response.isSuccess){
            emit(Result.Success(response.getOrThrow()))
        }else{
            emit(Result.Error(Exception(response.exceptionOrNull())))
        }
    }.catch {
        emit(Result.Error(Exception(it.message)))
    }
}