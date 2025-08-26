package com.example.wouple.preferences

import android.content.Context
import com.example.wouple.model.api.TemperatureUnit

object TemperatureUnitPref {
    private const val KEY = "temperatureUnitKey"
    fun getTemperatureUnit(context: Context): TemperatureUnit {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            ?: return TemperatureUnit.CELSIUS
        return sharedPref.getString(KEY, TemperatureUnit.CELSIUS.name)
            ?.let { TemperatureUnit.valueOf(it) } ?: TemperatureUnit.CELSIUS
    }

    fun setTemperatureUnit(context: Context, temperatureUnit: TemperatureUnit) {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(KEY, temperatureUnit.name)
            apply()
        }
    }
}