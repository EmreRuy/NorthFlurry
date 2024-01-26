package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentUnits(
    val european_aqi: String,
    val interval: String,
    val time: String
): Parcelable