package com.sandev.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat

@Composable
fun remNotifPermissionLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = onResult
    )

}

fun hasNotifPermission(context: Context): Boolean{
    return if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        )== PackageManager.PERMISSION_GRANTED
    }else{
        true
    }
}

fun getNotificationPermission(): String{
    return if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.POST_NOTIFICATIONS
    }else{
        ""
    }
}