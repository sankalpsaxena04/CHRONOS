package com.sandev.core.common


sealed class Result<out T>(){
    data class Success<T>(val data:T):Result<T>()
    data class Error(val exception:Throwable):Result<Nothing>()
    object Loading: Result<Nothing>()
}