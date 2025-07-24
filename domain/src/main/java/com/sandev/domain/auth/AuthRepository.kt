package com.sandev.domain.auth

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.sandev.core.common.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser:FirebaseUser?
    fun getSignInIntent(): Intent
    fun signInWithGoogle(idToken: String):Flow<Result<FirebaseUser?>>
    fun signOut(): Flow<Result<Unit>>
}