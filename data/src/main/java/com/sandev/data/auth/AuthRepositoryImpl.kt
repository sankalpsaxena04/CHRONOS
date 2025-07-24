package com.sandev.data.auth

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.sandev.core.common.Result
import com.sandev.domain.auth.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
): AuthRepository{
    override val currentUser: FirebaseUser?
        get() = authDataSource.currentUser

    override fun getSignInIntent(): Intent {
        return authDataSource.getGoogleSignInIntent()
    }

    override fun signInWithGoogle(idToken: String): Flow<Result<FirebaseUser?>> = flow {
        emit(Result.Loading)
        try {
            val user = authDataSource.signInWithGoogle(idToken)
            emit(Result.Success(user))
        }
        catch (e: Exception){
            emit(Result.Error(e))
        }
    }

    override fun signOut(): Flow<Result<Unit>> =flow{
        emit(Result.Loading)
        try {
            authDataSource.signOut()
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}