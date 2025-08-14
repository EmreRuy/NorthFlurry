package com.example.wouple.activities.startScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority // For LocationRequest priority
import java.util.Locale


class StartActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val defaultLocation = SearchedLocation("40.7128", "-74.0060", "New York")

    private var _locationStatus = mutableStateOf("Detecting location...")
    private var _isLocationReady = mutableStateOf(false)
    private var _isLoading = mutableStateOf(true)

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) getLocation()
            else {
                _locationStatus.value = "Falling back to: ${defaultLocation.display_name}"
                _isLocationReady.value = true
                _isLoading.value = false
                LocationPref.setSearchedLocation(this, defaultLocation)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MaterialTheme {
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
                // Try locality (city) first
                address.locality
                // If null, try subAdminArea (e.g., county)
                    ?: address.subAdminArea
                    // If null, try adminArea (e.g., state)
                    ?: address.adminArea
                    // If all fail, fall back to "Current Location"
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





