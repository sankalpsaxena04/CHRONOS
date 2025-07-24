package com.sandev.domain.reminder

import android.net.Uri
import com.sandev.domain.reminder.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import com.sandev.core.common.Result
import javax.inject.Inject

class AddReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val reminderValidator: ReminderValidator
) {
    operator fun invoke(userId: String, reminder: Reminder, imageUri: Uri?): Flow<Result<Unit>> = flow {
        val validationResult = reminderValidator.validate(reminder)
        if (validationResult.isValid) {
            emitAll(reminderRepository.addReminder(userId, reminder, imageUri))
        } else {
            emit(Result.Error(IllegalArgumentException(validationResult.errorMessage)))
        }
    }
}

class UpdateReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val reminderValidator: ReminderValidator
) {
    operator fun invoke(userId: String, reminder: Reminder, newImageUri: Uri?): Flow<Result<Unit>> = flow {
        val validationResult = reminderValidator.validate(reminder)
        if (validationResult.isValid) {
            emitAll(reminderRepository.updateReminder(userId, reminder, newImageUri))
        } else {
            emit(Result.Error(IllegalArgumentException(validationResult.errorMessage)))
        }
    }
}

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(userId: String, reminderId: String): Flow<Result<Unit>> {
        return reminderRepository.deleteReminder(userId, reminderId)
    }
}

class GetRemindersUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(userId: String): Flow<Result<List<Reminder?>>> {
        return reminderRepository.getReminders(userId)
    }
}

class GetReminderByIdUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(userId: String, reminderId: String): Flow<Result<Reminder?>> {
        return reminderRepository.getReminderById(userId, reminderId)
    }
}