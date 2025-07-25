package com.sandev.features.reminder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandev.domain.reminder.GetReminderByIdUseCase
import com.sandev.domain.reminder.model.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.sandev.core.common.Result
import com.sandev.domain.auth.GetSignedInUserUseCase
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReminderDetailViewModel @Inject constructor(
    private val getReminderByIdUseCase: GetReminderByIdUseCase,
    private val getCurrentUserUseCase: GetSignedInUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _reminder = MutableStateFlow<Result<Reminder?>?>(null)
    val reminder = _reminder.asStateFlow()


    private val currentUserId: String?
        get() = getCurrentUserUseCase()?.userId

    init {
        savedStateHandle.get<String>("reminderId")?.let { reminderId ->
            loadReminder(reminderId)
        }
    }

    fun loadReminder(reminderId: String) {
        currentUserId?.let { userId ->
            getReminderByIdUseCase(userId, reminderId).onEach { result ->
                _reminder.value = result
            }.launchIn(viewModelScope)
        } ?: run {
            _reminder.value = Result.Error(Exception("User not authenticated."))
        }
    }


}