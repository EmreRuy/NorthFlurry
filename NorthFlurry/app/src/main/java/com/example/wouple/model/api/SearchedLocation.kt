package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedLocation(
    val lat: String,
    val lon: String,
    val display_name: String
): Parcelable

data class OpenMeteoGeocodingResponse(
    val results: List<OpenMeteoLocationResult>? = null
)

data class OpenMeteoLocationResult(
    val id: Long? = null,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    val admin1: String? = null
) {
    fun toSearchedLocation(): SearchedLocation {
        val displayName = listOfNotNull(name, admin1, country)
            .filter { it.isNotBlank() }
            .distinct()
            .joinToString(", ")
        return SearchedLocation(
            lat = latitude.toString(),
            lon = longitude.toString(),
            display_name = displayName
        )
    }
}
