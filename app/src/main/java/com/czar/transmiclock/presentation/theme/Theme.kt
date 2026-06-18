package com.czar.transmiclock.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun TransmiClockTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            onSurface = Color.White,
            onBackground = Color.White
        ),
        content = content
    )
}