package com.sandev.domain.auth

data class SignInResult(
    val data:UserData?,
    val errorMessage: String?
)

data class UserData (
    val userId: String,
    val username:String?,
    val profilePic: String?
)
