package com.sandev.domain.di

import com.sandev.domain.reminder.AddReminderUseCase
import com.sandev.domain.reminder.DeleteReminderUseCase
import com.sandev.domain.reminder.GetReminderByIdUseCase
import com.sandev.domain.reminder.GetRemindersUseCase
import com.sandev.domain.reminder.ReminderRepository
import com.sandev.domain.reminder.ReminderValidator
import com.sandev.domain.reminder.UpdateReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReminderDomainModule {

    @Provides
    fun provideReminderValidator(): ReminderValidator {
        return ReminderValidator()
    }

    @Provides
    fun provideAddReminderUseCase(
        reminderRepository: ReminderRepository,
        reminderValidator: ReminderValidator
    ): AddReminderUseCase {
        return AddReminderUseCase(reminderRepository, reminderValidator)
    }

    @Provides
    fun provideUpdateReminderUseCase(
        reminderRepository: ReminderRepository,
        reminderValidator: ReminderValidator
    ): UpdateReminderUseCase {
        return UpdateReminderUseCase(reminderRepository, reminderValidator)
    }

    @Provides
    fun provideDeleteReminderUseCase(reminderRepository: ReminderRepository): DeleteReminderUseCase {
        return DeleteReminderUseCase(reminderRepository)
    }

    @Provides
    fun provideGetRemindersUseCase(reminderRepository: ReminderRepository): GetRemindersUseCase {
        return GetRemindersUseCase(reminderRepository)
    }

    @Provides
    fun provideGetReminderByIdUseCase(reminderRepository: ReminderRepository): GetReminderByIdUseCase {
        return GetReminderByIdUseCase(reminderRepository)
    }
}