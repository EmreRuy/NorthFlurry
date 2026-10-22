package com.example.wouple.elements

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wouple.data.preferences.PreferenceManager
import com.example.wouple.model.api.TemperatureUnit
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceManager = PreferenceManager(application.applicationContext)

    val temperatureUnit: StateFlow<TemperatureUnit> = preferenceManager.temperatureUnit

    fun updateTemperatureUnit(unit: TemperatureUnit) {
        preferenceManager.setTemperatureUnit(unit)
    }
}
