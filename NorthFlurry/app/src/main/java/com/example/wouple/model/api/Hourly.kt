package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hourly(
    val apparent_temperature: List<Double>,
    val dewpoint_2m: List<Double>,
    val is_day: List<Int>,
    val precipitation: List<Double>,
    val precipitation_probability: List<Int>,
    val relativehumidity_2m: List<Int>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val uv_index: List<Double>,
    val visibility: List<Double>,
    val weathercode: List<Int>,
    val windspeed_10m: List<Double>,
    val surface_pressure: List<Double>,
    val cloud_cover: List<Double>,
    val wind_direction_10m: List<Double>,
    ): Parcelable