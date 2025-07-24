package com.sandev.data.reminder.dto

import com.google.firebase.firestore.DocumentId
import com.sandev.domain.reminder.model.Reminder
import java.util.Date

data class ReminderDTO(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val dateTime: Long = 0,
    val note: String?=null,
    val imageUrl: String?=null,
    val createdAt: Long = Date().time
    ){
    fun toDomain(): Reminder {
        return Reminder(
            id = id,
            userId = userId,
            title = title,
            dateTime = Date(dateTime),
            notes = note,
            imageUri = imageUrl
        )
    }

    companion object{
        fun fromDomain(reminder: Reminder): ReminderDTO{
            return ReminderDTO(
                id = reminder.id,
                userId = reminder.userId,
                title = reminder.title,
                dateTime = reminder.dateTime.time,
                note = reminder.notes,
                imageUrl = reminder.imageUri
            )
        }
    }
}