package com.example.wouple.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF253650),
    onPrimary = Color(0xFFFEFFFF),
    primaryContainer = Color(0xFFB0C4DE),
    onPrimaryContainer = Color(0xFF121614),

    secondary = Color(0xFF4A6357),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD3E9DC),
    onSecondaryContainer = Color(0xFF354A3F),

    tertiary = Color(0xFF3D8FCE),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFEADDF1),
    onTertiaryContainer = Color(0xFF2B1C36),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFF6FBF4),
    onBackground = Color(0xFF161D18),

    surface = Color(0xFFE8F0E8),
    onSurface = Color(0xFF161D18),
    surfaceContainer = Color(0xFFDEE8DD),
    surfaceVariant = Color(0xFFF6FBF4),
    onSurfaceVariant = Color(0xFF333A34),

    outline = Color(0xFFDEE8DD),
    inverseOnSurface = Color(0xFFF0F1ED),
    inverseSurface = Color(0xFF2B322D),
    inversePrimary = Color(0xFF9EB4D4)
)
val WaveBlue = Color(0xFF254D4A)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6FA88C),
    onPrimary = Color(0xFF212828),          // deep navy for contrast
    primaryContainer = Color(0xFF2F3F5F),   // rich muted steel-blue
    onPrimaryContainer = Color(0xFFD8E3FF), // light bluish-white

    secondary = Color(0xFF9CC9B0),          // calm green-gray
    onSecondary = Color(0xFF0E2018),        // dark forest green
    secondaryContainer = Color(0xFF2D4338), // elegant deep green
    onSecondaryContainer = Color(0xFFCDEAD7), // minty highlight

    tertiary = Color(0xFF79B9FD),
    onTertiary = Color(0xFF261C37),         // dark plum
    tertiaryContainer = Color(0xFF3E2E54),  // rich purple
    onTertiaryContainer = Color(0xFFE9DDF7), // pale lavender

    error = Color(0xFFFFB4A9),              // softer red for dark mode
    onError = Color(0xFF680003),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD4),

    background = Color(0xFF121614),         // deep charcoal greenish-black
    onBackground = Color(0xFFE0E3DF),

    surface = Color(0xFF121614),            // same as background
    onSurface = Color(0xFFE0E3DF),
    surfaceVariant = Color(0xFF1B2121),     // elegant dark green-gray
    onSurfaceVariant = Color(0xFFBEC7C0),
    surfaceContainer = Color(0xFF272F2F),

    outline = Color(0xFF656C67),
    inverseOnSurface = Color(0xFF121614),
    inverseSurface = Color(0xFFE0E3DF),
    inversePrimary = Color(0xFF3E5C4A)      // desaturated blue
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
