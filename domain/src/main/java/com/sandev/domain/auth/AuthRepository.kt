package com.sandev.domain.auth

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.FirebaseUser
import com.sandev.core.common.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignInResult
    suspend fun signOut()
    fun getSignedInUser(): UserData?
}