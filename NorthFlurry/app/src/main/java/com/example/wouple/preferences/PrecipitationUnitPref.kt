package com.example.wouple.preferences

import android.content.Context
import com.example.wouple.model.api.PrecipitationUnit

object PrecipitationUnitPref {
    private const val key = "precipitationUnitKey"
    fun getPrecipitationUnit(context: Context): PrecipitationUnit {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            ?: return PrecipitationUnit.MM
        return sharedPref.getString(key, PrecipitationUnit.MM.name)
            ?.let { PrecipitationUnit.valueOf(it) } ?: PrecipitationUnit.MM
    }

    fun setPrecipitationUnit(context: Context, precipitationUnit: PrecipitationUnit) {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(key, precipitationUnit.name)
            apply()
        }
    }
}