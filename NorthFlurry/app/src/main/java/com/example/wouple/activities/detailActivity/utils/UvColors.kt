package com.example.wouple.activities.detailActivity.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun getUvColor(uvIndex: Float): Color {
    return when {
        uvIndex < 3 -> Color(0xFF4CAF50) // Low
        uvIndex < 6 -> Color(0xFFFFC107) // Moderate
        uvIndex < 8 -> Color(0xFFFF9800) // High
        uvIndex < 11 -> Color(0xFFF44336) // Very High
        else -> Color(0xFF9C27B0) // Extreme
    }
}