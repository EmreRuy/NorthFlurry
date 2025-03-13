package com.example.wouple.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3A6EA5),  // Deep ocean blue
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFBFD8EF),  // Soft sky blue
    onPrimaryContainer = Color(0xFF1C3E63),

    secondary = Color(0xFF64748B),  // Neutral blue-gray
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD8E0EB),
    onSecondaryContainer = Color(0xFF2C3A4B),

    tertiary = Color(0xFF886F6F),  // Elegant muted rose
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF4D7D7),
    onTertiaryContainer = Color(0xFF503636),

    background = Color(0xFFDCE2F9),  // Clean, light slate
    onBackground = Color(0xFF1F2933),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF202A36),

    outline = Color(0xFFB0BEC5),  // Subtle outlines
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),  // Light sky blue
    onPrimary = Color(0xFF0B2942),
    primaryContainer = Color(0xFF1E3A5F),
    onPrimaryContainer = Color(0xFFBFD8EF),

    secondary = Color(0xFF90A4AE),  // Soft desaturated blue-gray
    onSecondary = Color(0xFF1B262E),
    secondaryContainer = Color(0xFF2E3B44),
    onSecondaryContainer = Color(0xFFD8E0EB),

    tertiary = Color(0xFFCFAFAF),  // Warm rose tones
    onTertiary = Color(0xFF382222),
    tertiaryContainer = Color(0xFF573B3B),
    onTertiaryContainer = Color(0xFFF4D7D7),

    background = Color(0xFF0F172A),  // Deep navy for reduced eye strain
    onBackground = Color(0xFFDCE0E6),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFDCE0E6),

    outline = Color(0xFF78909C),  // Soft borders in dark mode
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
