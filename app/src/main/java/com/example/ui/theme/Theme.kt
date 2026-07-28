package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = QuantumNeonPurple,
    secondary = QuantumNeonBlue,
    tertiary = QuantumNeonGreen,
    background = QuantumDarkBg,
    surface = QuantumCardBg,
    onPrimary = QuantumLightText,
    onSecondary = QuantumLightText,
    onTertiary = QuantumLightText,
    onBackground = QuantumLightText,
    onSurface = QuantumLightText
)

private val LightColorScheme = DarkColorScheme

/**
 * Dynamic color is off by default: the game's identity is the fixed neon
 * cyberpunk palette, and letting Android 12+ substitute the device wallpaper
 * scheme washes it out.
 */
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
