package com.sandev.domain.reminder

import com.sandev.domain.reminder.model.Reminder
import jakarta.inject.Inject
import java.util.Date

data class ValidationResult (
    val isValid: Boolean,
    val errorMessage: String?=null
)
class ReminderValidator @Inject constructor() {
    fun validate(reminder: Reminder): ValidationResult {
        if (reminder.title.isBlank()) {
            return ValidationResult(false, "Title cannot be empty.")
        }
        if (reminder.dateTime.before(Date())) {
            return ValidationResult(false, "Reminder date and time cannot be in the past.")
        }
        return ValidationResult(true)
    }
}