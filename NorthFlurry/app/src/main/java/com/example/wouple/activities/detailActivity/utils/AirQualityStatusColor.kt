package com.example.wouple.activities.detailActivity.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun getAqiColor(aqi: Int): Color {
    return when (aqi) {
        in 0..20 -> Color(0xFF4CAF50)       // Good - Green
        in 21..40 -> Color(0xFF8BC34A)      // Fair - Light Green
        in 41..60 -> Color(0xFFFFEB3B)      // Moderate - Yellow
        in 61..80 -> Color(0xFFFF9800)      // Poor - Orange
        in 81..100 -> Color(0xFFF44336)     // Very Poor - Red
        in 101..Int.MAX_VALUE -> Color(0xFF9C27B0) // Hazardous - Purple
        else -> Color(0xFF9E9E9E)           // Unknown - Gray
    }
}