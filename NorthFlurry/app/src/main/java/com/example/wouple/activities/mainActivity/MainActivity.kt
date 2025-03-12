package com.example.wouple.activities.mainActivity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wouple.activities.firstScreen.FirstScreenView
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.activities.mainActivity.components.BottomNavigationBar
import com.example.wouple.activities.mainActivity.components.LoadingScreen
import com.example.wouple.activities.settingsActivity.SettingsActivity
import com.example.wouple.elements.NoInternetDialog
import com.example.wouple.elements.SettingsViewModel
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
import com.example.wouple.ui.theme.AppTheme
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.firstScreenColor
import com.example.wouple.ui.theme.mainColor
import com.example.wouple.ui.theme.mainViewColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocations: MutableState<List<SearchedLocation>?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)
    private val airQuality: MutableState<AirQuality?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirstLaunch = LocationPref.getSearchedLocation(this)
        setContent {
            AppTheme {
            val context = LocalContext.current
            var isConnected by remember { mutableStateOf(true) }
            var isLoading by remember { mutableStateOf(true) }
            LaunchedEffect(Unit) {
                isConnected = isInternetConnected(context)
                delay(1_000) // Simulating network delay for loading
                isLoading = false
                getWeatherData()
            }

            if (!isConnected) {
                // Shows dialog if there is no internet connection
                NoInternetDialog(activity = this)
            } else {
                if (isFirstLaunch == null) {
                    FirstScreenView(
                        onStartButtonClicked = {
                            Log.d("MainActivity", "Start button clicked")
                            val intent = Intent(this@MainActivity, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                } else {
                    if (temp.value == null || isLoading) {
                        LoadingScreen()
                    } else {
                        // Use BottomNavigationBar with MainView
                        val viewModel: SettingsViewModel = viewModel()
                        BottomNavigationBar(
                            temp = temp.value!!,
                            locations = searchedLocations.value,
                            onLocationButtonClicked = { location ->
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
                                    }
                                )
                            },
                            air = airQuality.value,
                            viewModel = viewModel,
                            onUnitSettingsChanged = { getWeatherData() }
                        )
                    }
                }
            }
            }
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

    private fun getAirQuality(location: SearchedLocation) {
        WeatherManager.getAirQuality(
            longitude = location.lon,
            latitude = location.lat,
            context = this
        ) {
            airQuality.value = it
        }
    }

    private fun getWeatherData() {
        searchedLocation.value = LocationPref.getSearchedLocation(this)
        searchedLocation.value?.let { location ->
            getCurrentWeather(
                context = this,
                location = location,
                temperaUnit = TemperatureUnitPref.getTemperatureUnit(this),
                windUnit = WindUnitPref.getWindUnit(this),
                precipitationUnit = PrecipitationUnitPref.getPrecipitationUnit(this),
                onSuccessCall = { temperature ->
                    temp.value = temperature
                }
            )
            getAirQuality(location)
        }
    }
}



