package com.example.wouple.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferenceManager(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _searchedLocation = MutableStateFlow(getSearchedLocation())
    val searchedLocation: StateFlow<SearchedLocation?> = _searchedLocation.asStateFlow()

    private val _temperatureUnit = MutableStateFlow(getTemperatureUnit())
    val temperatureUnit: StateFlow<TemperatureUnit> = _temperatureUnit.asStateFlow()

    private val _windUnit = MutableStateFlow(getWindUnit())
    val windUnit: StateFlow<WindUnit> = _windUnit.asStateFlow()

    private val _precipitationUnit = MutableStateFlow(getPrecipitationUnit())
    val precipitationUnit: StateFlow<PrecipitationUnit> = _precipitationUnit.asStateFlow()

    fun getSearchedLocation(): SearchedLocation? {
        val json = sharedPref.getString(KEY_LOCATION, null) ?: return null
        return try {
            gson.fromJson(json, SearchedLocation::class.java)
        } catch (_: Exception) {
            null
        }
    }

    fun setSearchedLocation(location: SearchedLocation) {
        val json = gson.toJson(location)
        sharedPref.edit().putString(KEY_LOCATION, json).apply()
        _searchedLocation.value = location
    }

    fun getTemperatureUnit(): TemperatureUnit {
        val name = sharedPref.getString(KEY_TEMP_UNIT, TemperatureUnit.CELSIUS.name)
        return try {
            name?.let { TemperatureUnit.valueOf(it) } ?: TemperatureUnit.CELSIUS
        } catch (_: Exception) {
            TemperatureUnit.CELSIUS
        }
    }

    fun setTemperatureUnit(unit: TemperatureUnit) {
        sharedPref.edit().putString(KEY_TEMP_UNIT, unit.name).apply()
        _temperatureUnit.value = unit
    }

    fun getWindUnit(): WindUnit {
        val name = sharedPref.getString(KEY_WIND_UNIT, WindUnit.KMH.name)
        return try {
            name?.let { WindUnit.valueOf(it) } ?: WindUnit.KMH
        } catch (_: Exception) {
            WindUnit.KMH
        }
    }

    fun setWindUnit(unit: WindUnit) {
        sharedPref.edit().putString(KEY_WIND_UNIT, unit.name).apply()
        _windUnit.value = unit
    }

    fun getPrecipitationUnit(): PrecipitationUnit {
        val name = sharedPref.getString(KEY_PRECIP_UNIT, PrecipitationUnit.MM.name)
        return try {
            name?.let { PrecipitationUnit.valueOf(it) } ?: PrecipitationUnit.MM
        } catch (_: Exception) {
            PrecipitationUnit.MM
        }
    }

    fun setPrecipitationUnit(unit: PrecipitationUnit) {
        sharedPref.edit().putString(KEY_PRECIP_UNIT, unit.name).apply()
        _precipitationUnit.value = unit
    }

    companion object {
        private const val PREF_NAME = "preferences"
        private const val KEY_LOCATION = "locationKey"
        private const val KEY_TEMP_UNIT = "temperatureUnitKey"
        private const val KEY_WIND_UNIT = "windUnitPrefKey"
        private const val KEY_PRECIP_UNIT = "precipitationUnitKey"
    }
}
