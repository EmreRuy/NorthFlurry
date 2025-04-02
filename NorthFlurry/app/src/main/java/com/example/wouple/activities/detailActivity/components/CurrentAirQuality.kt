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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun CurrentAirQualityCard(temp: TemperatureResponse, air: AirQuality?) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(12.dp),
    ) {
        AirQualityIndex(air, temp)
    }
}

@Composable
fun AirQualityIndex(air: AirQuality?, temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_airquality),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(id = R.string.air_quality_index).uppercase(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            val airCodex = air?.current?.european_aqi ?: 0
            val airQualityDescriptionResId = getAirQualityDescriptionResId(airCodex)
            val descriptionText = stringResource(id = airQualityDescriptionResId)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = descriptionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}