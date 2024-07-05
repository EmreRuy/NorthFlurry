package com.example.wouple.activities.startScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalFocusManager
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.manager.WeatherManager
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.LocationPref
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref

class StartActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocations: MutableState<List<SearchedLocation>?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val focusManager = LocalFocusManager.current
            StartScreenView(
                locations = searchedLocations.value,
                onSearch = { query ->
                    if (query.isNotEmpty()) {
                        WeatherManager.getSearchedLocations(
                            context = this,
                            address = query,
                            onSuccessCall = { location ->
                                searchedLocations.value = location
                            }
                        )
                    } else {
                        // Clears the list of locations when the query(searchBar) is empty
                        searchedLocations.value = emptyList()
                    }
                },
                onButtonClicked = { location ->
                    focusManager.clearFocus()
                    LocationPref.setSearchedLocation(this, location)
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                    finish()
                },
                searchedLocation = searchedLocation
            )

        }
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