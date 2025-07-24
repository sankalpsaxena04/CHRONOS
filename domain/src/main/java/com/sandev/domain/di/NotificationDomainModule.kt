package com.sandev.domain.di

import com.sandev.domain.notification.CancelReminderNotificationUseCase
import com.sandev.domain.notification.HasNotificationPermissionUseCase
import com.sandev.domain.notification.NotificationScheduler
import com.sandev.domain.notification.ScheduleReminderNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationDomainModule {

    @Provides
    fun provideScheduleReminderNotificationUseCase(
        notificationScheduler: NotificationScheduler
    ): ScheduleReminderNotificationUseCase {
        return ScheduleReminderNotificationUseCase(notificationScheduler)
    }

    @Provides
    fun provideCancelReminderNotificationUseCase(
        notificationScheduler: NotificationScheduler
    ): CancelReminderNotificationUseCase {
        return CancelReminderNotificationUseCase(notificationScheduler)
    }

    @Provides
    fun provideHasNotificationPermissionUseCase(
        notificationScheduler: NotificationScheduler
    ): HasNotificationPermissionUseCase {
        return HasNotificationPermissionUseCase(notificationScheduler)
    }
}