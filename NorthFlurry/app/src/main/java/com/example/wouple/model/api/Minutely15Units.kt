package com.example.wouple.model.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Minutely15Units(
    @Json(name = "lightning_potential")
    val lightningPotential: String,
    @Json(name = "time")
    val time: String
)