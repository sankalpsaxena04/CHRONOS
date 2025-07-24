package com.sandev.domain.auth

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.sandev.core.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): FirebaseUser?{
        return authRepository.currentUser
    }
}

class GetSignInIntentUseCase@Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke(): Intent{
        return authRepository.getSignInIntent()
    }
}

class SignInWithGoogleUseCase @Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke(idToken: String):Flow<Result<FirebaseUser?>>{
        return authRepository.signInWithGoogle(idToken)
    }
}

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke(): Flow<Result<Unit>>{
        return authRepository.signOut()
    }
}