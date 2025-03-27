package com.example.wouple.activities.startScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.manager.WeatherManager
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.LocationPref
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import com.example.wouple.ui.theme.AppTheme
import com.google.android.gms.location.LocationServices

class StartActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)

    // Create permission launcher
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, proceed to fetch location
                fetchLocation()
            } else {
                // Permission denied, fallback to random city
                fetchLocation(fallback = true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                FirstTimeLocationScreen(
                    onLocationDetected = {
                        checkLocationPermissions()
                    }
                )
            }
        }
    }

    private fun checkLocationPermissions() {
        when {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, fetch location
                fetchLocation()
            }
            else -> {
                // Request location permissions
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun fetchLocation(fallback: Boolean = false) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null && !fallback) {
                // If real location is found
                val searchedLocationObj = SearchedLocation(
                    lat = location.latitude.toString(),
                    lon = location.longitude.toString(),
                    display_name = location.toString() // or use a custom display name
                )
                LocationPref.setSearchedLocation(this, searchedLocationObj)
            } else {
                // If no real location is found, retry or use a random city
                handleLocationNotFound()
            }

            // Proceed to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleLocationNotFound() {
        // Retry fetching location or fallback to a random city after a short delay
        val randomCity = listOf("New York", "Los Angeles", "Paris", "Tokyo", "London", "Berlin").random()
        val searchedLocationObj = SearchedLocation(
            lat = "0.0", // Default latitude for unknown locations
            lon = "0.0", // Default longitude for unknown locations
            display_name = randomCity
        )
        LocationPref.setSearchedLocation(this, searchedLocationObj)
    }

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

