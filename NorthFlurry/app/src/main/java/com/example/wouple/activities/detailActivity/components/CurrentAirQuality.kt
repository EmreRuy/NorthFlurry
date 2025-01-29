package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Whitehis

@Composable
fun CurrentAirQualityCard(temp: TemperatureResponse, air: AirQuality?) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            Color(0xFF3D52BB),
            Color(0xFF3954C4),

            )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
        )
    }
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(12.dp),
    ) {
        AirQualityIndex(air, temp)
    }
}

@Composable
fun AirQualityIndex(air: AirQuality?, temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val isDay = temp.current_weather.is_day == 1
            val textColor =
                if (isDay) Whitehis.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.9f)
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_airquality),
                contentDescription = null,
                tint = textColor
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(id = R.string.air_quality_index).uppercase(),
                textAlign = TextAlign.Start,
                color = textColor.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium, // More readable than Light
                fontFamily = FontFamily.SansSerif, // Change to Inter if imported
                fontSize = 14.sp // Adjust for better readability
            )
            Spacer(modifier = Modifier.weight(1f))
            val aqiValue = air?.current?.european_aqi.toString()
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = aqiValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
            val airCodex = air?.current?.european_aqi ?: 0
            val airQualityDescriptionResId = getAirQualityDescriptionResId(airCodex)
            val descriptionText = stringResource(id = airQualityDescriptionResId)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = descriptionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
        }
    }
}