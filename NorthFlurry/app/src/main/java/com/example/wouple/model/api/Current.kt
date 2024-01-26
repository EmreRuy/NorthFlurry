package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Current(
    val european_aqi: Int,
    val interval: Int,
    val time: String
): Parcelable