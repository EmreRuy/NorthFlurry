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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class StartActivity : ComponentActivity() {

    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                fetchLocation()
            } else {
                fallbackToRandomCity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fetchLocation()
            }
            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun fetchLocation() {
        if (
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val searchedLocationObj = SearchedLocation(
                    lat = location.latitude.toString(),
                    lon = location.longitude.toString(),
                    display_name = "Current Location"
                )
                LocationPref.setSearchedLocation(this, searchedLocationObj)
                launchMainActivity()
            } else {
                fallbackToRandomCity()
            }
        }.addOnFailureListener {
            fallbackToRandomCity()
        }
    }

    private fun fallbackToRandomCity() {
        val randomCity = listOf("New York", "Los Angeles", "Paris", "Tokyo", "London", "Berlin").random()
        val fallbackLocation = SearchedLocation(
            lat = "0.0",
            lon = "0.0",
            display_name = randomCity
        )
        LocationPref.setSearchedLocation(this, fallbackLocation)
        launchMainActivity()
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
            }
        )
    }
}


