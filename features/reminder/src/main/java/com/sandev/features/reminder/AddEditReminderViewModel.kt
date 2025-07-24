package com.sandev.features.reminder

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.sandev.domain.auth.GetCurrentUserUseCase
import com.sandev.domain.notification.CancelReminderNotificationUseCase
import com.sandev.domain.notification.ScheduleReminderNotificationUseCase
import com.sandev.domain.reminder.AddReminderUseCase
import com.sandev.domain.reminder.DeleteReminderUseCase
import com.sandev.domain.reminder.GetReminderByIdUseCase
import com.sandev.domain.reminder.UpdateReminderUseCase
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
class AddEditReminderViewModel @Inject constructor(
    private val addReminderUseCase: AddReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val getReminderByIdUseCase: GetReminderByIdUseCase,
    private val getCurrentUserUseCase: GetSignedInUserUseCase,
    private val scheduleReminderNotificationUseCase: ScheduleReminderNotificationUseCase,
    private val cancelReminderNotificationUseCase: CancelReminderNotificationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _reminder = MutableStateFlow<Result<Reminder?>>(Result.Loading)
    val reminder = _reminder.asStateFlow()

    private val _saveReminderState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val saveReminderState = _saveReminderState.asStateFlow()

    private val currentUserId: String?
        get() = getCurrentUserUseCase()?.userId

    init {
        savedStateHandle.get<String>("reminderId")?.let { id ->
            if (id != "new") {
                loadReminder(id)
            }
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

    fun addReminder(reminder: Reminder, imageUri: Uri?) {
        currentUserId?.let { userId ->
            addReminderUseCase(userId, reminder.copy(userId = userId), imageUri).onEach { result ->
                _saveReminderState.value = result
                if (result is Result.Success) {
                    scheduleReminderNotificationUseCase(reminder.copy(userId = userId))
                }
            }.launchIn(viewModelScope)
        } ?: run {
            _saveReminderState.value = Result.Error(Exception("User not authenticated."))
        }
    }

    fun updateReminder(reminder: Reminder, newImageUri: Uri?) {
        currentUserId?.let { userId ->
            updateReminderUseCase(userId, reminder.copy(userId = userId), newImageUri).onEach { result ->
                _saveReminderState.value = result
                if (result is Result.Success) {
                    scheduleReminderNotificationUseCase(reminder.copy(userId = userId))
                }
            }.launchIn(viewModelScope)
        } ?: run {
            _saveReminderState.value = Result.Error(Exception("User not authenticated."))
        }
    }

    fun deleteReminder(reminderId: String) {
        currentUserId?.let { userId ->
            deleteReminderUseCase(userId, reminderId).onEach { result ->
                _saveReminderState.value = result
                if (result is Result.Success) {
                    cancelReminderNotificationUseCase(reminderId)
                }
            }.launchIn(viewModelScope)
        } ?: run {
            _saveReminderState.value = Result.Error(Exception("User not authenticated."))
        }
    }
}