package com.example.wouple.preferences

import android.content.Context

object WindUnitPref {
    fun getPrecipitationUnit(context: Context): String {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return "mm"
        val help = sharedPref.getString("precipitationUnitKey", "mm") ?: "mm"
        return help
    }

    fun setTemperatureUnit(context: Context, windaUnit: String) {

        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("precipitationUnitKey", windaUnit)
            apply()
        }
    }
}