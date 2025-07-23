package com.sandev.core.common.extensions

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Uri.getFileName(context: Context): String? {
    val cursor = context.contentResolver.query(this, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                return it.getString(nameIndex)
            }
        }
    }
    return null
}

// Function to copy URI content to a temporary file
fun Uri.copyToTempFile(context: Context, fileName: String): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this)
        val tempFile = File(context.cacheDir, fileName)
        FileOutputStream(tempFile).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        inputStream?.close()
        tempFile
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}