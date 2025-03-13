package com.example.wouple.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime

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

    background = Color(0xFF000000),  // Clean, light slate
    onBackground = Color(0xFF1F2933),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF202A36),

    outline = Color(0xFFB0BEC5),  // Subtle outlines
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF607D8B), // Muted blue-gray for cloudy
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF2C3E50),
    onPrimaryContainer = Color(0xFFB0BEC5),
    secondary = Color(0xFF607D8B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF2C3E50),
    onSecondaryContainer = Color(0xFFB0BEC5),
    background = Color(0xFF263238), // Dark gray for cloudy night
    onBackground = Color.White,
    surface = Color(0xFF37474F),
    onSurface = Color.White,
    outline = Color(0xFF78909C)
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

