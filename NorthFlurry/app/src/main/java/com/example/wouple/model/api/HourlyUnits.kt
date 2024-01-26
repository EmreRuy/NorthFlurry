package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourlyUnits(
    val apparent_temperature: String,
    val dewpoint_2m: String,
    val is_day: String,
    val precipitation_probability: String,
    val relativehumidity_2m: String,
    val temperature_2m: String,
    val time: String,
    val uv_index: String,
    val visibility: String,
    val weathercode: String,
    val windspeed_10m: String
): Parcelable