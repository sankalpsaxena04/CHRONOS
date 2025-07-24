package com.sandev.data.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
){
    private val googleSignInClient : GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("429210714618-jlu3acv531cq6ngtpuao6kcrcrukkog7.apps.googleusercontent.com")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context,gso)
    }
    val currentUser : FirebaseUser?
            get()= firebaseAuth.currentUser

    fun getGoogleSignInIntent() = googleSignInClient.signInIntent

    suspend fun signInWithGoogle(idToken: String): FirebaseUser?{
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        return firebaseAuth.signInWithCredential(credential).await().user
    }

    suspend fun signOut(){
        firebaseAuth.signOut()
        googleSignInClient.signOut().await()
    }
}