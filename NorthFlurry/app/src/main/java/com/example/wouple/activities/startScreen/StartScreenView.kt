package com.example.wouple.activities.startScreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.elements.SnowfallEffect
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewFirstTimeLocationScreen() {
    FirstTimeLocationScreen(onLocationDetected = {})
}

@Composable
fun FirstTimeLocationScreen(
    onLocationDetected: (String) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var locationText by remember { mutableStateOf("Detecting location...") }
    var isLocationDetected by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    val showSnowfall = remember { mutableStateOf(true) }
    val searchBarAppear = remember { mutableStateOf(false) }

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
        delay(1_000)
        showSnowfall.value = true // Shows snowfall effect when the screen appears
        delay(6_000) // snowfall effect time
        showSnowfall.value = false // Hides snowfall effect after 6 seconds
        locationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION) // triggers location permission check
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(24.dp),
        contentAlignment = Center
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showSnowfall.value) {
                SnowfallEffect(searchBarAppear)
            }

            if (!showSnowfall.value) {
                // Shows content after snowfall effect
                Text(
                    text = "Welcome to NorthFlurry!",
                    fontSize = 24.sp,
                    fontStyle = MaterialTheme.typography.displayLarge.fontStyle,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                } else {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(92.dp),
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = locationText,
                            fontSize = 18.sp,
                            fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                            fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                if (isLocationDetected) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            val selectedCity = locationText
                            val cityCoordinates = mapOf(
                                "New York" to Pair("40.7128", "-74.0060"),
                                "Los Angeles" to Pair("34.0522", "-118.2437"),
                                "Paris" to Pair("48.8566", "2.3522"),
                                "Tokyo" to Pair("35.6895", "139.6917"),
                                "London" to Pair("51.5074", "-0.1278"),
                                "Berlin" to Pair("52.5200", "13.4050")
                            )
                            val (lat, lon) = cityCoordinates[selectedCity] ?: Pair("0.0", "0.0")
                            val searchedLocationObj = SearchedLocation(
                                lat = lat,
                                lon = lon,
                                display_name = selectedCity
                            )
                            LocationPref.setSearchedLocation(context, searchedLocationObj)
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Continue", color = MaterialTheme.colorScheme.primaryContainer, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}







