package com.example.wouple.activities.startScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.activities.splashScreen.Navigation
import com.example.wouple.manager.WeatherManager
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.LocationPref
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import com.google.gson.Gson

class StartActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocations: MutableState<List<SearchedLocation>?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           StartScreenView(
               locations = searchedLocations.value,
               onSearch = { query ->
                   WeatherManager.getSearchedLocations(
                       context = this,
                       address = query,
                       onSuccessCall = { location ->
                           searchedLocations.value = location
                       })
               },
               onButtonClicked = { location ->
                   onLocationButtonClicked(location)
                   val intent = Intent(this, MainActivity::class.java)
                   intent.putExtra("location", searchedLocation.value)
                   this.startActivity(intent)
               },
               searchedLocation = searchedLocation
           )

        }
    }
    private fun onLocationButtonClicked(location: SearchedLocation) {
        LocationPref.setSearchedLocation(this, location)
        WeatherManager.getCurrentWeather(
            context = this,
            location = location,
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
            temperaUnit = TemperatureUnitPref.getTemperatureUnit(this),
            windUnit = WindUnitPref.getWindUnit(this),
            precipitationUnit = PrecipitationUnitPref.getPrecipitationUnit(this)
        )
        searchedLocations.value = null
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onResume() {
        super.onResume()
        searchedLocation.value = LocationPref.getSearchedLocation(this)
        WeatherManager.getCurrentWeather(
            context = this,
            location = searchedLocation.value,
            temperaUnit = TemperatureUnitPref.getTemperatureUnit(this),
            precipitationUnit = PrecipitationUnitPref.getPrecipitationUnit(this),
            windUnit = WindUnitPref.getWindUnit(this),
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
        )
    }
}