package com.sandev.data.auth

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.FirebaseUser
import com.sandev.core.common.Result
import com.sandev.domain.auth.AuthRepository
import com.sandev.domain.auth.SignInResult
import com.sandev.domain.auth.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository{
    override suspend fun signIn() = dataSource.signIn()
    override suspend fun signInWithIntent(intent: Intent) = dataSource.signInWithIntent(intent)
    override suspend fun signOut() = dataSource.signOut()
    override fun getSignedInUser() = dataSource.getSignedInUser()

}