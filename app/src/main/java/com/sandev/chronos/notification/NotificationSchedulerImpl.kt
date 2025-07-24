package com.sandev.chronos.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sandev.domain.notification.NotificationScheduler
import com.sandev.domain.reminder.model.Reminder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val workManager: WorkManager
) : NotificationScheduler {

    override fun scheduleReminder(reminder: Reminder) {
        val now = Calendar.getInstance().timeInMillis
        val reminderTime = reminder.dateTime.time
        val delay = reminderTime - now

        if (delay <= 0) {
            return
        }

        val reminderData = Data.Builder()
            .putString("REMINDER_ID", reminder.id)
            .putString("REMINDER_TITLE", reminder.title)
            .putString("REMINDER_NOTES", reminder.notes)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val notificationWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(reminderData)
            .setConstraints(constraints)
            .addTag(reminder.id)
            .build()

        workManager.enqueueUniqueWork(
            reminder.id,
            ExistingWorkPolicy.REPLACE,
            notificationWorkRequest
        )
    }

    override fun cancelReminder(reminderId: String) {
        workManager.cancelAllWorkByTag(reminderId)
    }

    override fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
