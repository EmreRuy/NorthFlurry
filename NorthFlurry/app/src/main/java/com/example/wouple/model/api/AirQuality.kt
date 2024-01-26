package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AirQuality(
    val current: Current,
    val current_units: CurrentUnits,
): Parcelable