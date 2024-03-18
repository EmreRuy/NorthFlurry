package com.example.wouple.activities.mainActivity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalFocusManager
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.activities.settingsActivity.SettingsActivity
import com.example.wouple.manager.WeatherManager
import com.example.wouple.manager.WeatherManager.getCurrentWeather
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.LocationPref
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import com.example.wouple.ui.theme.WoupleTheme

class MainActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    // The list of all locations when searching
    private val searchedLocations: MutableState<List<SearchedLocation>?> = mutableStateOf(null)
    //The current location the user is having
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)
    private val airQuality: MutableState<AirQuality?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        setContent {
            WoupleTheme {
                if (isFirstLaunch)
                    NoTemperatureView(
                        onStartButtonClicked = {
                            Log.d("NoTemperatureView", "Start button clicked")
                            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
                            val intent = Intent(this@MainActivity, StartActivity::class.java)
                            startActivity(intent)
                            val secondIntent = Intent(this, StartActivity::class.java)
                            intent.putExtra("searchVisible", true)
                            startActivity(secondIntent)
                            finish()
                        }
                    )
                else {
                    displayFirstCardView()
                }
            }
        }
    }
    private fun displayFirstCardView() {
        setContent {
            val focusManager = LocalFocusManager.current
            if (temp.value !== null) {
                FirstCardView(
                    temp = temp.value!!,
                    locations = searchedLocations.value,
                    onLocationButtonClicked = { location ->
                        focusManager.clearFocus()
                        onLocationButtonClicked(location)
                    },
                    searchedLocation = searchedLocation,
                    onClose = { searchedLocations.value = null },
                    onSearch = { query ->
                        WeatherManager.getSearchedLocations(
                            context = this,
                            address = query,
                            onSuccessCall = { location ->
                                searchedLocations.value = location
                            })
                    },
                    onDetailsButtonClicked = { temp ->
                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("temp", temp)
                        intent.putExtra("air", airQuality.value)
                        intent.putExtra("location", searchedLocation.value)
                        intent.putExtra("precipitationUnit", PrecipitationUnitPref.getPrecipitationUnit(this) )
                        intent.putExtra("wind_unit", WindUnitPref.getWindUnit(this))
                        this.startActivity(intent)
                    },
                    /*   onTemperatureUnitChanged = { temperatureUnit ->
                           val precipitationUnit = WindUnitPref.getPrecipitationUnit(this)
                           TemperatureUnitPref.setTemperatureUnit(this, temperatureUnit)
                           WindUnitPref.setTemperatureUnit(this, precipitationUnit)
                           getCurrentWeather(
                               context = this,
                               location = searchedLocation.value,
                               temperaUnit = temperatureUnit,
                               precipitationUnit = precipitationUnit ,
                               onSuccessCall = { temperature ->
                                   temp.value = temperature
                               })
                           searchedLocation.value?.let {
                               getAirQuality(
                                   it
                               )
                           }
                       } */
                    onSettingsClicked = {
                        val intent = Intent(this, SettingsActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
    private fun getAirQuality(location: SearchedLocation) {
        WeatherManager.getAirQuality(
            longitude = location.lon,
            latitude = location.lat,
            context = this
        ) {
            airQuality.value = it
        }
    }
    private fun onLocationButtonClicked(location: SearchedLocation) {
        LocationPref.setSearchedLocation(this, location)
        getCurrentWeather(
            context = this,
            location = location,
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
            temperaUnit = TemperatureUnitPref.getTemperatureUnit(this),
            windUnit = WindUnitPref.getWindUnit(this) ,
            precipitationUnit = PrecipitationUnitPref.getPrecipitationUnit(this)
        )
        getAirQuality(location)
        searchedLocations.value = null
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onResume() {
        super.onResume()
        searchedLocation.value = LocationPref.getSearchedLocation(this)
        getCurrentWeather(
            context = this,
            location = searchedLocation.value,
            temperaUnit = TemperatureUnitPref.getTemperatureUnit(this),
            windUnit = WindUnitPref.getWindUnit(this) ,
            precipitationUnit = PrecipitationUnitPref.getPrecipitationUnit(this),
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
        )
        searchedLocation.value?.let {
            getAirQuality(it)
        }
    }
}

