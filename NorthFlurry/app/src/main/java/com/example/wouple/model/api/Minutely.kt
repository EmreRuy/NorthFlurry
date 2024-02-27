package com.example.wouple.model.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Minutely(
    @Json(name = "elevation")
    val elevation: Int,
    @Json(name = "generationtime_ms")
    val generationtimeMs: Double,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "minutely_15")
    val minutely15: Minutely15,
    @Json(name = "minutely_15_units")
    val minutely15Units: Minutely15Units,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_abbreviation")
    val timezoneAbbreviation: String,
    @Json(name = "utc_offset_seconds")
    val utcOffsetSeconds: Int
)