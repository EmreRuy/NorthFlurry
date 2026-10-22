package com.example.wouple.data.repository

import com.example.wouple.data.api.RetrofitClient
import com.example.wouple.data.api.WeatherApiService
import com.example.wouple.data.preferences.PreferenceManager
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val apiService: WeatherApiService = RetrofitClient.apiService,
    private val preferenceManager: PreferenceManager
) {

    val searchedLocation: StateFlow<SearchedLocation?> = preferenceManager.searchedLocation
    val temperatureUnit: StateFlow<TemperatureUnit> = preferenceManager.temperatureUnit
    val windUnit: StateFlow<WindUnit> = preferenceManager.windUnit
    val precipitationUnit: StateFlow<PrecipitationUnit> = preferenceManager.precipitationUnit

    suspend fun fetchCurrentWeather(location: SearchedLocation): Result<TemperatureResponse> =
        withContext(Dispatchers.IO) {
            try {
                val tempUnit = preferenceManager.getTemperatureUnit().name.lowercase()
                val windUnit = preferenceManager.getWindUnit().name.lowercase()
                val precipUnit = preferenceManager.getPrecipitationUnit().name.lowercase()

                val response = apiService.getTemperature(
                    latitude = location.lat,
                    longitude = location.lon,
                    temperatureUnit = tempUnit,
                    windSpeedUnit = windUnit,
                    precipitationUnit = precipUnit
                )
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun fetchAirQuality(location: SearchedLocation): Result<AirQuality> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAirQuality(
                    latitude = location.lat,
                    longitude = location.lon
                )
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun searchLocations(query: String): Result<List<SearchedLocation>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getSearchedLocations(address = query)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    fun saveSearchedLocation(location: SearchedLocation) {
        preferenceManager.setSearchedLocation(location)
    }

    fun getSavedLocation(): SearchedLocation? {
        return preferenceManager.getSearchedLocation()
    }

    fun setTemperatureUnit(unit: TemperatureUnit) {
        preferenceManager.setTemperatureUnit(unit)
    }

    fun setWindUnit(unit: WindUnit) {
        preferenceManager.setWindUnit(unit)
    }

    fun setPrecipitationUnit(unit: PrecipitationUnit) {
        preferenceManager.setPrecipitationUnit(unit)
    }
}
