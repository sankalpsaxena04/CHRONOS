package com.sandev.core.common.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToDateTime(): String {
    return SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(this)
}

fun Date.formatToDate(): String {
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(this)
}

fun Date.formatToTime(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}