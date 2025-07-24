package com.sandev.domain.auth

data class SignInState(
    val isSignInSuccess: Boolean = false,
    val signInError: String? = null
)