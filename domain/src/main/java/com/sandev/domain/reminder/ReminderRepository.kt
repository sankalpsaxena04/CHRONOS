package com.sandev.domain.reminder

import android.net.Uri
import com.sandev.core.common.Result
import com.sandev.domain.reminder.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    fun addReminder(userId: String,reminder: Reminder,imageUri: Uri?): Flow<Result<Unit>>

    fun updateReminder(userId:String, reminder: Reminder,newImage:Uri?): Flow<Result<Unit>>

    fun deleteReminder(userId: String,reminderId: String): Flow<Result<Unit>>

    fun getReminders(userId: String): Flow<Result<List<Reminder?>>>

    fun getReminderById(userId: String,reminderId: String): Flow<Result<Reminder?>>
}