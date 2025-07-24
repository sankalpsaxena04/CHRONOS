package com.sandev.chronos.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sandev.chronos.MainActivity
import com.sandev.core.common.Constants
import kotlin.jvm.java


class ReminderWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context,workerParameters)
{
    @androidx.annotation.RequiresPermission(
        android.Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val reminderId = inputData.getString("REMINDER_ID")?:return Result.failure()
        val reminderTitle = inputData.getString("REMINDER_TITLE")?:"Reminder"
        val reminderNote = inputData.getString("REMINDER_NOTES")?:"Reminder..."

        createNotificationChannel()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, Constants.REMINDER_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(reminderTitle)
            .setContentText(reminderNote)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context))  {
            notify(Constants.REMINDER_NOTIFICATION_ID, builder.build())
        }

        return Result.success()

        }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = Constants.REMINDER_NOTIFICATION_CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constants.REMINDER_NOTIFICATION_CHANNEL_ID,name,importance)
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}