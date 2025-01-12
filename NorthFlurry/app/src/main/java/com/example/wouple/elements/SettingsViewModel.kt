package com.example.wouple.elements

import androidx.lifecycle.ViewModel
import com.example.wouple.model.api.TemperatureUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel()  {
    private val _temperatureUnit = MutableStateFlow(TemperatureUnit.CELSIUS)
    val temperatureUnit: StateFlow<TemperatureUnit> = _temperatureUnit

    fun updateTemperatureUnit(unit: TemperatureUnit) {
        _temperatureUnit.value = unit
    }
}