package com.example.wouple.activities.startScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import com.example.wouple.ui.theme.AppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority // For LocationRequest priority
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse


class StartActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient // For checking location settings

    private val _locationStatus = mutableStateOf("Detecting your location...")
    private val _isLocationReady = mutableStateOf(false)
    private val _isLoadingLocation = mutableStateOf(true)

    // Define the LocationCallback
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location: Location? = locationResult.lastLocation
            if (location != null) {
                Log.d(
                    "StartActivity",
                    "Fresh location update received: Lat=${location.latitude}, Lon=${location.longitude}"
                )
                stopLocationUpdates() // Stop updates once we get a location
                handleLocationResult(location) // Process the location
            } else {
                Log.w("StartActivity", "Received null location from update, still waiting...")
            }
        }
    }

    // Define the LocationRequest
    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdateDelayMillis(15000)
            .build()

    // NEW LAUNCHER: For resolving location settings issues
    private val resolveLocationSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.d(
                    "StartActivity",
                    "User enabled location services. Re-attempting location fetch."
                )
                requestLocationUpdates() // User enabled, try to get location again
            } else {
                Log.w(
                    "StartActivity",
                    "User did NOT enable location services. Falling back to random city."
                )
                fallbackToRandomCity() // User declined to enable, fall back
            }
        }


    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("StartActivity", "Location permission GRANTED.")
                fetchLocation()
            } else {
                Log.d("StartActivity", "Location permission DENIED. Falling back to random city.")
                fallbackToRandomCity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this) // Initialize settingsClient

        setContent {
            AppTheme {
                FirstTimeLocationScreen(
                    locationStatus = _locationStatus.value,
                    isLocationReady = _isLocationReady.value,
                    isLoading = _isLoadingLocation.value,
                    onAttemptPermissionRequest = {
                        Log.d("StartActivity", "Attempting permission request.")
                        checkLocationPermissions()
                    },
                    onContinueClicked = { finalizedLocation ->
                        LocationPref.setSearchedLocation(this, finalizedLocation)
                        launchMainActivity()
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
                Log.d("StartActivity", "Permission already granted, fetching location.")
                fetchLocation()
            }

            else -> {
                Log.d("StartActivity", "Permission not granted, launching permission dialog.")
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun fetchLocation() {
        _locationStatus.value = "Detecting your location..."
        _isLoadingLocation.value = true
        _isLocationReady.value = false

        if (
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(
                "StartActivity",
                "Permissions missing during fetchLocation call, should not happen!"
            )
            fallbackToRandomCity()
            return
        }

        // Try last known location first
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                Log.d(
                    "StartActivity",
                    "Last known location found: Lat=${location.latitude}, Lon=${location.longitude}"
                )
                handleLocationResult(location)
            } else {
                Log.w(
                    "StartActivity",
                    "Last known location is NULL. Checking device settings and requesting fresh location updates."
                )
                // If lastLocation is null, check device settings and then request active updates
                checkLocationSettingsAndRequestUpdates()
            }
        }.addOnFailureListener { e ->
            Log.e("StartActivity", "Failed to get last known location: ${e.message}", e)
            // If even lastLocation fails, check device settings and then try requesting updates
            checkLocationSettingsAndRequestUpdates()
        }
    }

    private fun checkLocationSettingsAndRequestUpdates() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest) // Add our desired location request
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener { response: LocationSettingsResponse ->
            // All location settings are satisfied. The client can initialize location requests.
            Log.d("StartActivity", "Location settings are ON (GPS etc.). Starting updates.")
            requestLocationUpdates()
        }

        task.addOnFailureListener { exception: Exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                Log.w("StartActivity", "Location settings NOT satisfied. Prompting user to enable.")
                try {
                    // Show the dialog by calling startIntentSenderForResult().
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    resolveLocationSettingsLauncher.launch(intentSenderRequest)
                } catch (sendEx: Exception) {
                    // Ignore the error.
                    Log.e(
                        "StartActivity",
                        "Error showing location settings dialog: ${sendEx.message}",
                        sendEx
                    )
                    fallbackToRandomCity() // Fallback if dialog can't be shown
                }
            } else {
                // Location settings are not satisfied, and cannot be fixed by showing a dialog.
                Log.e(
                    "StartActivity",
                    "Location settings NOT satisfied and not resolvable. Falling back to random city. Exception: ${exception.message}",
                    exception
                )
                fallbackToRandomCity()
            }
        }
    }


    private fun handleLocationResult(location: Location) {
        val searchedLocationObj = SearchedLocation(
            lat = location.latitude.toString(),
            lon = location.longitude.toString(),
            display_name = "Current Location"
        )
        _locationStatus.value = "Location Detected: Current Location"
        _isLocationReady.value = true
        _isLoadingLocation.value = false
        LocationPref.setSearchedLocation(this, searchedLocationObj)
    }

    private fun requestLocationUpdates() {
        if (
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("StartActivity", "Permissions lost before requesting updates (safety check)!")
            fallbackToRandomCity()
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
            .addOnSuccessListener {
                Log.d("StartActivity", "Successfully started requesting location updates.")
            }
            .addOnFailureListener { e ->
                Log.e(
                    "StartActivity",
                    "Failed to start location updates AFTER settings check: ${e.message}",
                    e
                )
                fallbackToRandomCity()
            }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("StartActivity", "Stopped location updates.")
    }

    private fun fallbackToRandomCity() {
        val randomCity =
            listOf("New York", "Los Angeles", "Paris", "Tokyo", "London", "Berlin").random()
        val fallbackLocation = SearchedLocation(
            lat = "0.0",
            lon = "0.0",
            display_name = randomCity
        )
        Log.d("StartActivity", "Falling back to random city: $randomCity")
        _locationStatus.value = "Falling back to: $randomCity"
        _isLocationReady.value = true
        _isLoadingLocation.value = false
        LocationPref.setSearchedLocation(this, fallbackLocation)
    }

    private fun launchMainActivity() {
        Log.d("StartActivity", "Launching MainActivity.")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }
}


