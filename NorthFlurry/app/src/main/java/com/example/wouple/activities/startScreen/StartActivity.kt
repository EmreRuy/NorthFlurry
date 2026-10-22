package com.example.wouple.activities.startScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.ui.start.StartViewModel
import com.example.wouple.ui.theme.AppTheme
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient

class StartActivity : ComponentActivity() {

    private val viewModel: StartViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                checkLocationSettings()
            } else {
                viewModel.fallbackLocation()
            }
        }

    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getLocation()
            } else {
                viewModel.fallbackLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)

        setContent {
            AppTheme {
                val locationStatus by viewModel.locationStatus.collectAsState()
                val isLocationReady by viewModel.isLocationReady.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()

                FirstTimeLocationScreen(
                    locationStatus = locationStatus,
                    isLocationReady = isLocationReady,
                    isLoading = isLoading,
                    onAttemptPermissionRequest = {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                    onContinueClicked = { selectedLocation ->
                        viewModel.saveAndFinish(selectedLocation)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }

    private fun checkLocationSettings() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000).build()
        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(request)
            .setAlwaysShow(true)
            .build()

        settingsClient.checkLocationSettings(settingsRequest)
            .addOnSuccessListener {
                getLocation()
            }
            .addOnFailureListener { ex ->
                if (ex is ResolvableApiException) {
                    try {
                        resolutionForResult.launch(
                            IntentSenderRequest.Builder(ex.resolution).build()
                        )
                    } catch (_: Exception) {
                        viewModel.fallbackLocation()
                    }
                } else {
                    viewModel.fallbackLocation()
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                viewModel.updateLocation(loc.latitude, loc.longitude)
            } else {
                requestSingleLocationUpdate()
            }
        }.addOnFailureListener {
            viewModel.fallbackLocation()
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
                        viewModel.updateLocation(loc.latitude, loc.longitude)
                    } else {
                        viewModel.fallbackLocation()
                    }
                    fusedLocationClient.removeLocationUpdates(this)
                }
            },
            mainLooper
        )
    }
}
