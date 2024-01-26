package com.example.wouple.preferences

import android.content.Context
import com.google.gson.Gson

object TemperatureUnitPref {
    fun getTemperatureUnit(context: Context): String {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return "Celsius"
        val help = sharedPref.getString("temperatureUnitKey", "celsius") ?: "Celsius"
        return help
    }

    fun setTemperatureUnit(context: Context, temperaUnit: String) {

        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("temperatureUnitKey", temperaUnit)
            apply()
        }
    }
}