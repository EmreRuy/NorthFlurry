package com.example.wouple.activities.startScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.app.ActivityCompat
import com.example.wouple.activities.firstScreen.FirstScreenView
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.elements.SnowfallEffect
import com.example.wouple.manager.WeatherManager
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.LocationPref
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay

class StartActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstTimeLocationScreen(
                onLocationDetected = { locationText ->
                    // Fetch latitude and longitude based on the detected location
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    when {
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED -> {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                        }
                    }
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            // If real location is found
                            val searchedLocationObj = SearchedLocation(
                                lat = location.latitude.toString(),
                                lon = location.longitude.toString(),
                                display_name = locationText
                            )
                            LocationPref.setSearchedLocation(this, searchedLocationObj)
                        } else {
                            // If no real location is found, use a random city
                            val searchedLocationObj = SearchedLocation(
                                lat = "0.0", // Default latitude for unknown locations
                                lon = "0.0", // Default longitude for unknown locations
                                display_name = locationText
                            )
                            LocationPref.setSearchedLocation(this, searchedLocationObj)
                        }

                        // Proceed to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
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
