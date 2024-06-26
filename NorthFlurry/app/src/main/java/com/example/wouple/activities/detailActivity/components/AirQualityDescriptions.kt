package com.example.wouple.activities.detailActivity.components

import androidx.annotation.StringRes
import com.example.wouple.R

@StringRes
fun getAirQualityDescriptionResId(aqi: Int): Int {
    return when (aqi) {
        in 0..20 -> R.string.good
        in 21..40 -> R.string.fair
        in 41..60 -> R.string.moderate
        in 61..80 -> R.string.poor
        in 81..100 -> R.string.very_poor
        in 101..Int.MAX_VALUE -> R.string.hazardous
        else -> R.string.unknown
    }
}