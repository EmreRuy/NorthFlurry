package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis

@Composable
fun ExtraCards(
    text: String,
    numbers: String,
    // icon: Painter,
    temp: TemperatureResponse
) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            Color(0xFF3F54BE),
            Color(0xFF3F54BE)
        )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
            Color(0xFF50767D),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(120.dp)
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .shadow(1.dp, RoundedCornerShape(21.dp))
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontSize = 22.sp,

                )

            Text(
                modifier = Modifier.padding(8.dp),
                text = numbers,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

        }
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Expected_Today),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 16.sp
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.3f,
            amplitude = 80f,
            frequency = 0.6f
        )
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 15f),
            alpha = 0.2f,
            amplitude = 60f,
            frequency = 0.4f
        )

    }
}