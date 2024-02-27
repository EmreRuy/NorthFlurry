package com.example.wouple.model.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Minutely15(
    @Json(name = "lightning_potential")
    val lightningPotential: List<Int>,
    @Json(name = "time")
    val time: List<String>
)