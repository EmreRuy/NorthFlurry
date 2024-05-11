package com.example.wouple.activities.mainActivity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.activities.settingsActivity.SettingsActivity
import com.example.wouple.elements.NoInternetDialog
import com.example.wouple.elements.isInternetConnected
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
        val isFirstLaunch = LocationPref.getSearchedLocation(this)
        setContent {
            val context = LocalContext.current
            var isConnected by remember { mutableStateOf(true) }
            LaunchedEffect(true) {
                isConnected = isInternetConnected(context)
            }
            if (!isConnected) {
                // Shows dialog if no internet connection
                NoInternetDialog(activity = this)
            } else {
            WoupleTheme {
                if (isFirstLaunch == null)
                    NoTemperatureView(
                        onStartButtonClicked = {
                            Log.d("NoTemperatureView", "Start button clicked")
                            val intent = Intent(this@MainActivity, StartActivity::class.java)
                            startActivity(intent)
                            val secondIntent = Intent(this, StartActivity::class.java)
                            intent.putExtra("searchVisible", true)
                            startActivity(secondIntent)
                            finish()
                        }
                    )
                else {
                    displayFirstCardView(activity = this)
                }
            }
        }
        }
    }

    private fun displayFirstCardView(activity: ComponentActivity) {
        setContent {
            val context = LocalContext.current
            var isConnected by remember { mutableStateOf(true) }
            LaunchedEffect(true) {
                isConnected = isInternetConnected(context)
            }
            if (!isConnected) {
                NoInternetDialog(activity)
            } else {
                val focusManager = LocalFocusManager.current
                if (temp.value !== null) {
                    MainView(
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
                            intent.putExtra(
                                "precipitationUnit",
                                PrecipitationUnitPref.getPrecipitationUnit(this)
                            )
                            intent.putExtra("wind_unit", WindUnitPref.getWindUnit(this))
                            this.startActivity(intent)
                        },
                        onSettingsClicked = { temp ->
                            val intent = Intent(this, SettingsActivity::class.java)
                            intent.putExtra("temp", temp)
                            this.startActivity(intent)
                        }
                    )
                }
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
            windUnit = WindUnitPref.getWindUnit(this),
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
            windUnit = WindUnitPref.getWindUnit(this),
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

