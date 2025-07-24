package com.sandev.domain.auth

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.FirebaseUser
import com.sandev.core.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun beginSignIn(): IntentSender? = repository.signIn()
    suspend fun signInWithIntent(intent: Intent): SignInResult = repository.signInWithIntent(intent)
}

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}


class GetSignedInUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): UserData? {
        return repository.getSignedInUser()
    }
}