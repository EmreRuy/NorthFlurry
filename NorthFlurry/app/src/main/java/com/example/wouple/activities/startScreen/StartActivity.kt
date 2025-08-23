package com.example.wouple.activities.startScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import com.example.wouple.ui.theme.AppTheme
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority // For LocationRequest priority
import com.google.android.gms.location.SettingsClient
import java.util.Locale


class StartActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient

    private val defaultLocation = SearchedLocation("40.7128", "-74.0060", "New York")

    private var _locationStatus = mutableStateOf("Detecting location...")
    private var _isLocationReady = mutableStateOf(false)
    private var _isLoading = mutableStateOf(true)

    // Launcher for permission request
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                checkLocationSettings() // checks location services
            } else {
                fallbackLocation()
            }
        }

    // Launcher for location settings dialog
    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // User enabled location
                getLocation()
            } else {
                // User declined → fallback
                fallbackLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)

        setContent {
            AppTheme {
                FirstTimeLocationScreen(
                    locationStatus = _locationStatus.value,
                    isLocationReady = _isLocationReady.value,
                    isLoading = _isLoading.value,
                    onAttemptPermissionRequest = {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                    onContinueClicked = { selectedLocation ->
                        LocationPref.setSearchedLocation(this, selectedLocation)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }

    /**
    Check if location settings are enabled.
    If not, ask user to enable them.
     */
    private fun checkLocationSettings() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000).build()
        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(request)
            .setAlwaysShow(true) // shows dialog if location is off
            .build()

        settingsClient.checkLocationSettings(settingsRequest)
            .addOnSuccessListener {
                // All settings satisfied → safe to get location
                getLocation()
            }
            .addOnFailureListener { ex ->
                if (ex is ResolvableApiException) {
                    try {
                        resolutionForResult.launch(
                            IntentSenderRequest.Builder(ex.resolution).build()
                        )
                    } catch (_: Exception) {
                        fallbackLocation()
                    }
                } else {
                    fallbackLocation()
                }
            }
    }

    //Gets last known location or request single update
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        _isLoading.value = true
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                updateLocation(loc.latitude, loc.longitude)
            } else {
                requestSingleLocationUpdate()
            }
        }.addOnFailureListener {
            fallbackLocation()
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun requestSingleLocationUpdate() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000
        ).setMaxUpdates(1).build()

        fusedLocationClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val loc = result.lastLocation
                    if (loc != null) {
                        updateLocation(loc.latitude, loc.longitude)
                    } else {
                        fallbackLocation()
                    }
                    fusedLocationClient.removeLocationUpdates(this)
                }
            },
            mainLooper
        )
    }

    private fun updateLocation(lat: Double, lon: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val locationName = try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses.first()
                address.locality
                    ?: address.subAdminArea
                    ?: address.adminArea
                    ?: "Current Location"
            } else {
                "Current Location"
            }
        } catch (_: Exception) {
            "Current Location"
        }

        _locationStatus.value = "Location Detected: $locationName"
        _isLocationReady.value = true
        _isLoading.value = false
        LocationPref.setSearchedLocation(
            this,
            SearchedLocation(lat.toString(), lon.toString(), locationName)
        )
    }

    private fun fallbackLocation() {
        _locationStatus.value = "Falling back to: ${defaultLocation.display_name}"
        _isLocationReady.value = true
        _isLoading.value = false
        LocationPref.setSearchedLocation(this, defaultLocation)
    }
}




