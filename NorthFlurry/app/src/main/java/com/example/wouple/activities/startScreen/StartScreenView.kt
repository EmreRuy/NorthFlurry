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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.MainActivity
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

    var locationText by remember { mutableStateOf("Detecting your location...") }
    var isLocationDetected by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    val randomCities = listOf("New York", "Los Angeles", "Paris", "Tokyo", "London", "Berlin")

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        isLoading = false
        if (granted) {
            fetchLocation(context, fusedLocationClient) { city ->
                locationText = city ?: randomCities.random()
                isLocationDetected = true
            }
        } else {
            locationText = randomCities.random()
            isLocationDetected = true
        }
    }

    LaunchedEffect(Unit) {
        delay(800)
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3C4043), Color(0xFF3C4043))
                )
            ),
        contentAlignment = Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            LottieAnimationSection()

            Text(
                text = "Welcome to NorthFlurry!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = locationText,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else if (isLocationDetected) {
                Button(
                    onClick = {
                        val cityCoordinates = mapOf(
                            "New York" to ("40.7128" to "-74.0060"),
                            "Los Angeles" to ("34.0522" to "-118.2437"),
                            "Paris" to ("48.8566" to "2.3522"),
                            "Tokyo" to ("35.6895" to "139.6917"),
                            "London" to ("51.5074" to "-0.1278"),
                            "Berlin" to ("52.5200" to "13.4050")
                        )
                        val (lat, lon) = cityCoordinates[locationText] ?: ("0.0" to "0.0")

                        val searchedLocation = SearchedLocation(lat, lon, locationText)
                        LocationPref.setSearchedLocation(context, searchedLocation)

                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as? Activity)?.finish()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue",
                        color = Color(0xFF4C49C6),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
fun LottieAnimationSection() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sun))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    )
}





