package com.example.wouple.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wouple.data.preferences.PreferenceManager
import com.example.wouple.data.repository.WeatherRepository
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository = WeatherRepository(
        preferenceManager = PreferenceManager(application.applicationContext)
    )

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        val location = repository.getSavedLocation()
        if (location == null) {
            _uiState.value = MainUiState.Error("No location selected")
            return
        }

        viewModelScope.launch {
            _uiState.value = MainUiState.Loading

            val tempResult = repository.fetchCurrentWeather(location)
            val airResult = repository.fetchAirQuality(location)

            tempResult.fold(
                onSuccess = { tempResp ->
                    val airResp = airResult.getOrNull()
                    val currentSearchResults =
                        (_uiState.value as? MainUiState.Success)?.searchedLocationsResults
                    _uiState.value = MainUiState.Success(
                        temperatureResponse = tempResp,
                        searchedLocation = location,
                        airQuality = airResp,
                        searchedLocationsResults = currentSearchResults
                    )
                },
                onFailure = { error ->
                    _uiState.value = MainUiState.Error(error.localizedMessage ?: "Failed to load weather data")
                }
            )
        }
    }

    fun selectLocation(location: SearchedLocation) {
        repository.saveSearchedLocation(location)
        clearSearchResults()
        loadWeatherData()
    }

    fun searchLocations(query: String) {
        if (query.isBlank()) {
            clearSearchResults()
            return
        }

        viewModelScope.launch {
            val result = repository.searchLocations(query)
            result.onSuccess { list ->
                val currentState = _uiState.value
                if (currentState is MainUiState.Success) {
                    _uiState.value = currentState.copy(searchedLocationsResults = list)
                }
            }
        }
    }

    fun clearSearchResults() {
        val currentState = _uiState.value
        if (currentState is MainUiState.Success) {
            _uiState.value = currentState.copy(searchedLocationsResults = null)
        }
    }

    fun updateTemperatureUnit(unit: TemperatureUnit) {
        repository.setTemperatureUnit(unit)
        loadWeatherData()
    }

    fun updateWindUnit(unit: WindUnit) {
        repository.setWindUnit(unit)
        loadWeatherData()
    }

    fun updatePrecipitationUnit(unit: PrecipitationUnit) {
        repository.setPrecipitationUnit(unit)
        loadWeatherData()
    }
}
