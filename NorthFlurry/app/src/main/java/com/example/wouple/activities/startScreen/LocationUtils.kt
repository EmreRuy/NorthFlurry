package com.example.wouple.activities.startScreen


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*

fun fetchLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFetched: (String?) -> Unit
) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                getCityName(context, location.latitude, location.longitude, onLocationFetched)
            } else {
                onLocationFetched(null)
            }
        }.addOnFailureListener {
            onLocationFetched(null)
        }
    } else {
        onLocationFetched(null)
    }
}

@Suppress("DEPRECATION")
fun getCityName(
    context: Context,
    latitude: Double,
    longitude: Double,
    onResult: (String?) -> Unit
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
            val cityName = addresses.firstOrNull()?.locality
            onResult(cityName)
        }
    } else {
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val cityName = addresses?.firstOrNull()?.locality
            onResult(cityName)
        } catch (_: Exception) {
            onResult(null)
        }
    }
}
