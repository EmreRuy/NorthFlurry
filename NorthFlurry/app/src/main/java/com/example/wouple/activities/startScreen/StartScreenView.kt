package com.example.wouple.activities.startScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    locationStatus: String,
    isLocationReady: Boolean,
    isLoading: Boolean,
    onAttemptPermissionRequest: () -> Unit,
    onContinueClicked: (SearchedLocation) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(800)
        onAttemptPermissionRequest()
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

    // A more themable gradient
    val gradients = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f),
        MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradients)),
        contentAlignment = Center
    ) {
        // A Box to create an elevated card-like surface
        Box(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(vertical = 48.dp, horizontal = 24.dp),
            contentAlignment = Center
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                LottieAnimationSection()

                Text(
                    text = "Welcome to NorthFlurry!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = displayedLocationName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = isLocationReady && !isLoading,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 200)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500))
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
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            // This is the correct place to set the content color for contrast
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Continue",
                            style = MaterialTheme.typography.titleMedium.copy(
                                // Remove the color override and let contentColor handle it
                                // color = MaterialTheme.colorScheme.surface
                            ),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
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




