package com.sandev.chronos

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ChronosApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val config = Configuration.Builder()
            .build()
        WorkManager.initialize(this, config)
    }
}