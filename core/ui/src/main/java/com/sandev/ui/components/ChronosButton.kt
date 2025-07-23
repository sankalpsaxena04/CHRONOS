package com.sandev.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChronosButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick:()-> Unit,
    enabled: Boolean = true
){
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = text)
    }
}