package com.example.wouple.activities.mainActivity.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun WeatherOracleCard(temp: TemperatureResponse) {
    val oracleMessage = remember { getOraclePrediction(temp) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
            MaterialTheme.colorScheme.surfaceVariant
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Set to transparent
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // Top header — stays solid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .padding(start = 12.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔮 Weather Oracle says:",
                    fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 15.sp
                )
            }

            // Bottom content — with gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = gradientBrush)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)) // match Card corners
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    AnimatedContent(
                        targetState = oracleMessage,
                        transitionSpec = {
                            fadeIn() + slideInVertically { fullHeight -> fullHeight } togetherWith fadeOut()
                        },
                        label = "OracleMessageAnimation"
                    ) { targetMessage ->
                        SparklingText(text = targetMessage)
                    }
                }
            }
        }
    }
}


fun getOraclePrediction(temp: TemperatureResponse): String {
    val condition = temp.current_weather.weathercode.toString()
    val t = temp.current_weather.temperature
    val wind = temp.current_weather.windspeed

    return when {
        condition.contains("6") -> "☔ Umbrella? Yes. Regrets? None."
        t > 32 -> "It's basically lava out there. Hydrate or evaporate. 🔥 "
        t < 0 -> "Frostbite wants a word. Bundle up like a burrito. ❄️ "
        wind > 20 -> "The wind writes poetry on your face. Maybe stay in? 🌬️ "
        else -> "Today is a vibe. Go outside before it changes its mind. 🌈 "
    }
}

@Composable
fun SparklingText(text: String, modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "SparkleTransition")

    val animatedAlpha = transition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AlphaAnimation"
    )

    val animatedColor = transition.animateColor(
        initialValue = MaterialTheme.colorScheme.onSurface,
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ColorAnimation"
    )

    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            color = animatedColor.value.copy(alpha = animatedAlpha.value)
        ),
        modifier = modifier
    )
}