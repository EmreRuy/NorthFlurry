package com.example.wouple.activities.startScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority // For LocationRequest priority



class StartActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) getLocation()
            else useFallbackLocation()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermissionAndFetch()
    }

    private fun checkPermissionAndFetch() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                saveLocation(loc.latitude, loc.longitude, "Current Location")
            } else {
                requestSingleLocationUpdate()
            }
        }.addOnFailureListener {
            useFallbackLocation()
        }
    }

    private fun requestSingleLocationUpdate() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
        ).setMaxUpdates(1).build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocationClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val loc = result.lastLocation
                    if (loc != null) {
                        saveLocation(loc.latitude, loc.longitude, "Current Location")
                    } else {
                        useFallbackLocation()
                    }
                    fusedLocationClient.removeLocationUpdates(this)
                }
            },
            mainLooper
        )
    }

    private fun saveLocation(lat: Double, lon: Double, name: String) {
        LocationPref.setSearchedLocation(
            this,
            SearchedLocation(lat.toString(), lon.toString(), name)
        )
        launchMain()
    }

    private fun useFallbackLocation() {
        val fallback = SearchedLocation("0.0", "0.0", "New York")
        LocationPref.setSearchedLocation(this, fallback)
        launchMain()
    }

    private fun launchMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}




