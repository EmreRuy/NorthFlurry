package com.example.wouple.elements

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    // Private mutable StateFlow for unit preferences
    private val _temperatureUnit = MutableStateFlow(TemperatureUnit.CELSIUS)
    private val _precipitationUnit = MutableStateFlow(PrecipitationUnit.MM)
    private val _windUnit = MutableStateFlow(WindUnit.MS)

    // Public immutable StateFlow for unit preferences
    val temperatureUnit: StateFlow<TemperatureUnit> get() = _temperatureUnit
    val precipitationUnit: StateFlow<PrecipitationUnit> get() = _precipitationUnit
    val windUnit: StateFlow<WindUnit> get() = _windUnit

    // Method to update the selected temperature unit
    fun updateTemperatureUnit(context: Context, unit: TemperatureUnit) {
        TemperatureUnitPref.setTemperatureUnit(context, unit)
        _temperatureUnit.value = unit
    }

    // Method to update the selected precipitation unit
    fun updatePrecipitationUnit(context: Context, unit: PrecipitationUnit) {
        PrecipitationUnitPref.setPrecipitationUnit(context, unit)
        _precipitationUnit.value = unit
    }

    // Method to update the selected wind unit
    fun updateWindUnit(context: Context, unit: WindUnit) {
        WindUnitPref.setWindUnit(context, unit)
        _windUnit.value = unit
    }

    // Initialize the unit preferences from SharedPreferences
    fun loadUnits(context: Context) {
        viewModelScope.launch {
            _temperatureUnit.value = TemperatureUnitPref.getTemperatureUnit(context)
            _precipitationUnit.value = PrecipitationUnitPref.getPrecipitationUnit(context)
            _windUnit.value = WindUnitPref.getWindUnit(context)
        }
    }
}