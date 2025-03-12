package com.example.wouple.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF505B92),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDDE1FF),
    onPrimaryContainer = Color(0xFF384379),

    secondary = Color(0xFF5A5D72),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFDFE1F9),
    onSecondaryContainer = Color(0xFF424659),

    tertiary = Color(0xFF76546E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD7F3),
    onTertiaryContainer = Color(0xFF5C3C56),
    background = Color(0xFFF7F9FC),  // Light bluish-gray, soft on the eyes
    onBackground = Color(0xFF202124)
)
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB9C3FF),
    onPrimary = Color(0xFF202C61),
    primaryContainer = Color(0xFF384379),
    onPrimaryContainer = Color(0xFFDDE1FF),

    secondary = Color(0xFFC3C5DD),
    onSecondary = Color(0xFF2C2F42),
    secondaryContainer = Color(0xFF424659),
    onSecondaryContainer = Color(0xFFDFE1F9),

    tertiary = Color(0xFFE5BAD9),
    onTertiary = Color(0xFF44263E),
    tertiaryContainer = Color(0xFF5C3C56),
    onTertiaryContainer = Color(0xFFFFD7F3),
    background = Color(0xFF121212),  // Almost black, comfortable in low light
    onBackground = Color(0xFFE3E3E3)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detects system theme
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colors = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
