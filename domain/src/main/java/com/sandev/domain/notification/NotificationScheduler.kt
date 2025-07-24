package com.sandev.domain.notification

import com.sandev.domain.reminder.model.Reminder

interface NotificationScheduler {
    fun scheduleReminder(reminder: Reminder)
    fun cancelReminder(reminderId: String)
    fun hasNotificationPermission(): Boolean
}