package com.sandev.domain.notification

import com.sandev.domain.reminder.model.Reminder
import jakarta.inject.Inject

class ScheduleReminderNotificationUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {
    operator fun invoke(reminder: Reminder) {
        notificationScheduler.scheduleReminder(reminder)
    }
}

class CancelReminderNotificationUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {
    operator fun invoke(reminderId: String) {
        notificationScheduler.cancelReminder(reminderId)
    }
}

class HasNotificationPermissionUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {
    operator fun invoke(): Boolean {
        return notificationScheduler.hasNotificationPermission()
    }
}