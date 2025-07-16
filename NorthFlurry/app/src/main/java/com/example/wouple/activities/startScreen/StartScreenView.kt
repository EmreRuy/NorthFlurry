package com.example.wouple.activities.startScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.preferences.LocationPref
import kotlinx.coroutines.delay


@Composable
fun FirstTimeLocationScreen(
    // State values passed from StartActivity
    locationStatus: String,
    isLocationReady: Boolean,
    isLoading: Boolean,
    // Callbacks to communicate back to StartActivity
    onAttemptPermissionRequest: () -> Unit,
    onContinueClicked: (SearchedLocation) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(800) // Small delay for UX
        onAttemptPermissionRequest() // Inform StartActivity to handle permission check/request
    }

    val displayedLocationName = remember(locationStatus) {
        when {
            locationStatus.startsWith("Location Detected: ") -> locationStatus.replace(
                "Location Detected: ",
                ""
            )

            locationStatus.startsWith("Falling back to: ") -> locationStatus.replace(
                "Falling back to: ",
                ""
            )

            else -> locationStatus
        }
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
                text = displayedLocationName,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
                CircularProgressIndicator(color = Color.White)
            }

            AnimatedVisibility(
                visible = isLocationReady && !isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = {
                        val finalizedLocation = LocationPref.getSearchedLocation(context)
                            ?: SearchedLocation("0.0", "0.0", displayedLocationName)
                        onContinueClicked(finalizedLocation)
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
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    )
}





