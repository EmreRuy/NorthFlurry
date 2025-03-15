package com.example.wouple.activities.startScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.elements.GetPulsatingEffect
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.SnowfallEffect
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun FirstTimeLocationScreen(
    onLocationDetected: (String) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var locationText by remember { mutableStateOf("Detecting location...") }
    var isLocationDetected by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // Define a list of random cities
    val randomCities = listOf("New York", "Los Angeles", "Paris", "Tokyo", "London", "Berlin")

    val locationPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            fetchLocation(context, fusedLocationClient) { cityName ->
                isLoading = false
                locationText = cityName ?: randomCities.random()
                isLocationDetected = true
            }
        } else {
            isLoading = false
            locationText = randomCities.random()
            isLocationDetected = true
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        locationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    val darkBackground = listOf(
        Color(0xFF1D244D),
        Color(0xFF2E3A59),
        Color(0xFF3F5066),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(darkBackground))
            .padding(24.dp),
        contentAlignment = Center
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to NorthFlurry!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(color = White)
            } else {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                        contentDescription = null,
                        tint = Unspecified
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = locationText,
                        fontSize = 18.sp,
                        color = White
                    )
                }
            }
            if (isLocationDetected) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        val selectedCity = locationText
                        // Assign proper coordinates based on random city (you can expand this list)
                        val cityCoordinates = mapOf(
                            "New York" to Pair("40.7128", "-74.0060"),
                            "Los Angeles" to Pair("34.0522", "-118.2437"),
                            "Paris" to Pair("48.8566", "2.3522"),
                            "Tokyo" to Pair("35.6895", "139.6917"),
                            "London" to Pair("51.5074", "-0.1278"),
                            "Berlin" to Pair("52.5200", "13.4050")
                        )
                        val (lat, lon) = cityCoordinates[selectedCity] ?: Pair("0.0", "0.0")

                        // Save location to preferences
                        val searchedLocationObj = SearchedLocation(
                            lat = lat,
                            lon = lon,
                            display_name = selectedCity
                        )
                        LocationPref.setSearchedLocation(context, searchedLocationObj)

                        // Proceed to MainActivity
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as? Activity)?.finish()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Text("Continue", color = White, fontSize = 18.sp)
                }

            }
        }
    }
}

// Updated fetchLocation function
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
        }
    } else {
        onLocationFetched(null)
    }
}

// Updated getCityName using async geocoder
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
        } catch (e: Exception) {
            onResult(null)
        }
    }
}




