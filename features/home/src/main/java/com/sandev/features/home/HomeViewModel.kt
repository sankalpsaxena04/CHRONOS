package com.sandev.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
//import com.sandev.domain.auth.GetCurrentUserUseCase
//import com.sandev.domain.auth.SignOutUseCase
import com.sandev.domain.notification.HasNotificationPermissionUseCase
import com.sandev.domain.reminder.GetRemindersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.sandev.core.common.Result
import com.sandev.core.network.AiService
import com.sandev.domain.auth.SignOutUseCase
import com.sandev.domain.reminder.model.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val hasNotificationPermissionUseCase: HasNotificationPermissionUseCase,
    private val aiService: AiService
) : ViewModel() {

    private val _remindersState = MutableStateFlow<Result<List<Reminder?>>>(Result.Loading)
    val remindersState = _remindersState.asStateFlow()

    private val _signOutState = MutableStateFlow<Result<Unit>?>(null)
    val signOutState = _signOutState.asStateFlow()

    private val _aiGreetingState = MutableStateFlow<Result<String>>(Result.Loading)
    val aiGreetingState = _aiGreetingState.asStateFlow()


    fun loadReminders() {
        val userId = Firebase.auth.currentUser?.uid
        if (userId != null) {
            getRemindersUseCase(userId).onEach { result ->
                _remindersState.value = result
            }.launchIn(viewModelScope)
        } else {
            _remindersState.value = Result.Error(Exception("User not authenticated."))
        }
    }

    fun signOut() {
        _signOutState.value = Result.Loading
        viewModelScope.launch {
            try {
                signOutUseCase()
                _signOutState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _signOutState.value = Result.Error(e)
            }
        }

    }

    fun hasNotificationPermission(): Boolean {
        return hasNotificationPermissionUseCase()
    }

    fun shareAiGreeting() {
        viewModelScope.launch {
            _aiGreetingState.value = Result.Loading
            try {
                val encodedPrompt =  URLEncoder.encode("Give a warm Greetings from Sankalp the developer od Chronos", StandardCharsets.UTF_8.toString())
                val greeting = aiService.getGreetings(encodedPrompt)
                if(greeting.isSuccessful){
                    greeting.body()?.let {
                        _aiGreetingState.value = Result.Success(it)
                    }
                }
                else{
                    _aiGreetingState.value = Result.Error(Exception(greeting.errorBody().toString()))
                }


            } catch (e: Exception) {
                _aiGreetingState.value = Result.Error(e)
                Log.d("ChronosErrorTag",e.message.toString())
            }
        }
    }


}