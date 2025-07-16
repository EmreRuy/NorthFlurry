package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun WeatherOracleCard(temp: TemperatureResponse) {
    val oracleMessage = remember { getOraclePrediction(temp) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🔮 Weather Oracle says:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = oracleMessage,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun getOraclePrediction(temp: TemperatureResponse): String {
    val condition = temp.current_weather.weathercode.toString()
    val t = temp.current_weather.temperature
    val wind = temp.current_weather.windspeed

    return when {
        condition.contains("6") -> "☔ Umbrella? Yes. Regrets? None."
        t > 32 -> "🔥 It's basically lava out there. Hydrate or evaporate."
        t < 0 -> "❄️ Frostbite wants a word. Bundle up like a burrito."
        wind > 20 -> "🌬️ The wind writes poetry on your face. Maybe stay in?"
        else -> "🌈 Today is a vibe. Go outside before it changes its mind."
    }
}
