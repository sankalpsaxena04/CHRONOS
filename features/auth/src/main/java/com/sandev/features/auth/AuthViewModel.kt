package com.sandev.features.auth

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandev.domain.auth.GetSignedInUserUseCase
import com.sandev.domain.auth.SignInResult
import com.sandev.domain.auth.SignInState
import com.sandev.domain.auth.SignInUseCase
import com.sandev.domain.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getSignedInUserUseCase: GetSignedInUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        getSignedInUser()
    }

    fun onSignInResult(result: SignInResult) {
        _authState.update {
            it.copy(
                isSignInSuccess = result.data != null && result.errorMessage == null,
                signInError = result.errorMessage
            )
        }
    }

    fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            val result = signInUseCase.signInWithIntent(intent)
            onSignInResult(result)
        }
    }

    fun beginSignIn(onResult: (IntentSender?) -> Unit) {
        viewModelScope.launch {
            onResult(signInUseCase.beginSignIn())
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _authState.value = AuthState()
        }
    }

    private fun getSignedInUser() {
        val user = getSignedInUserUseCase()
        _authState.update {
            it.copy(
                isSignInSuccess = user != null
            )
        }
    }
}
data class AuthState(
    val isSignInSuccess: Boolean = false,
    val signInError: String? = null
)
