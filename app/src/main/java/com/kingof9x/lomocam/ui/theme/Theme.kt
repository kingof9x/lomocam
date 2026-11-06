package com.kingof9x.lomocam.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LomoColors = darkColorScheme(
    primary = Color(0xFFE8572A),
    onPrimary = Color.Black,
    background = Color.Black,
    surface = Color(0xFF1B1B1B),
    onBackground = Color(0xFFF2E6D0),
    onSurface = Color(0xFFF2E6D0),
)

@Composable
fun LomoCamTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LomoColors, content = content)
}
