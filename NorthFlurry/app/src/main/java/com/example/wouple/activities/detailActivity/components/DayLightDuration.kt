package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.components.LottieAnimationShootingStar
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.mocassin

@Composable
fun DayLightDuration(temp: TemperatureResponse, explodeConfettiCallback: () -> Unit) {
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
        )
    }
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(
                shape = RoundedCornerShape(20.dp),
                brush = Brush.verticalGradient(background)
            )
            .padding(8.dp)
    ) {
        GetDaylightDuration(temp = temp, explodeConfettiCallback)
    }
}


@Composable
private fun GetDaylightDuration(temp: TemperatureResponse, explodeConfettiCallback: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.Daylight_Duration),
            color = Whitehis,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        val formattedSunset = getFormattedSunset(temp)
        if (formattedSunset.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GetSunRise(temp)
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowdropup),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            tint = Whitehis.copy(alpha = 0.9f),
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrowdropdown),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            tint = Whitehis.copy(alpha = 0.8f),
                        )
                    }
                    GetSunSet(temp)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(70.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    explodeConfettiCallback()
                                }),
                        painter = painterResource(id = R.drawable.logo2),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GetDayLength(temp)
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.baseline_error_outline_24),
                    contentDescription = "error",
                    tint = mocassin
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "No Data Available for Display",
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    color = mocassin,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}