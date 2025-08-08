package com.example.wouple.activities.detailActivity.utils

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.wouple.R


data class AirQualityInfo(
    @StringRes val descriptionResId: Int,
    val color: Color
)

fun getAirQualityInfo(aqi: Int): AirQualityInfo {
    return when (aqi) {
        in 0..20 -> AirQualityInfo(R.string.good, Color(0xFF4CAF50))
        in 21..40 -> AirQualityInfo(R.string.fair, Color(0xFF8BC34A))
        in 41..60 -> AirQualityInfo(R.string.moderate, Color(0xFFFF8C00))
        in 61..80 -> AirQualityInfo(R.string.poor, Color(0xFFFF7700))
        in 81..100 -> AirQualityInfo(R.string.very_poor, Color(0xFFF44336))
        in 101..Int.MAX_VALUE -> AirQualityInfo(R.string.hazardous, Color(0xFF9C27B0))
        else -> AirQualityInfo(R.string.unknown, Color(0xFF9E9E9E))
    }
}
