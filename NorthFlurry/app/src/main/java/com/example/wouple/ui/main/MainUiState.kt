package com.example.wouple.ui.main

import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Success(
        val temperatureResponse: TemperatureResponse,
        val searchedLocation: SearchedLocation?,
        val airQuality: AirQuality?,
        val searchedLocationsResults: List<SearchedLocation>? = null
    ) : MainUiState
    data class Error(val message: String) : MainUiState
}
