package com.example.wouple.preferences

import android.content.Context
import com.example.wouple.model.api.WindUnit

object WindUnitPref {

    private const val key = "windUnitPrefKey"
    fun getWindUnit(context: Context): WindUnit {
        val sharedPref =
            context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return WindUnit.KMH
        return sharedPref.getString(key, WindUnit.KMH.name)?.let { WindUnit.valueOf(it) }
            ?: WindUnit.KMH
    }

    fun setWindUnit(context: Context, windUnit: WindUnit) {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(key, windUnit.name)
            apply()
        }
    }
}