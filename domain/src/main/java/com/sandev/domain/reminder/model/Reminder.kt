package com.sandev.domain.reminder.model

import java.util.Date

data class Reminder(
    val id: String="",
    val userId: String="",
    val title: String,
    val dateTime: Date,
    val notes:String?=null,
    val imageUri: String?=null
    )
