package com.sandev.data.reminder

import android.net.Uri
import com.sandev.core.common.Result
import com.sandev.data.reminder.dto.ReminderDTO
import com.sandev.domain.reminder.ReminderRepository
import com.sandev.domain.reminder.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor( private val reminderDataSource: ReminderDataSource):
    ReminderRepository{
    override fun addReminder(
        userId: String,
        reminder: Reminder,
        imageUri: Uri?
    ): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        try {
            var imageUrl: String?=null
            if(imageUri!=null){
                imageUrl = reminderDataSource.uploadImage(userId,imageUri)
            }
            val reminderDTO = ReminderDTO.fromDomain(reminder.copy(imageUri = imageUrl))
            reminderDataSource.addReminder(userId,reminderDTO)
            emit(Result.Success(Unit))
        }
        catch (e: Exception){
            emit(Result.Error(e))
        }
    }

    override fun updateReminder(
        userId: String,
        reminder: Reminder,
        newImage: Uri?
    ): Flow<Result<Unit>>  = flow {
        emit(Result.Loading)
        try {
            val oldReminder = reminderDataSource.getReminderById(userId, reminder.id)
            var currentImageUrl = oldReminder?.imageUrl

            if (newImage != null && newImage.toString() != currentImageUrl) {
                oldReminder?.imageUrl?.let { reminderDataSource.deleteImage(it) }
                currentImageUrl = reminderDataSource.uploadImage(userId, newImage)
            } else if (newImage == null && currentImageUrl != null && reminder.imageUri == null) {
                reminderDataSource.deleteImage(currentImageUrl)
                currentImageUrl = null
            }

            val reminderDTO = ReminderDTO.fromDomain(reminder.copy(imageUri = currentImageUrl))
            reminderDataSource.updateReminder(userId, reminderDTO)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun deleteReminder(
        userId: String,
        reminderId: String
    ): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        try {
            val reminderToDelete = reminderDataSource.getReminderById(userId, reminderId)
            reminderToDelete?.imageUrl?.let { reminderDataSource.deleteImage(it) }
            reminderDataSource.deleteReminder(userId, reminderId)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getReminders(userId: String): Flow<Result<List<Reminder?>>> = flow {
        emit(Result.Loading)
        try {
            reminderDataSource.getReminders(userId).collect { dtos ->
                emit(Result.Success(dtos.map { it.toDomain() }))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getReminderById(
        userId: String,
        reminderId: String
    ): Flow<Result<Reminder?>> = flow {
        emit(Result.Loading)
        try {
            val reminderDto = reminderDataSource.getReminderById(userId, reminderId)
            emit(Result.Success(reminderDto?.toDomain()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}