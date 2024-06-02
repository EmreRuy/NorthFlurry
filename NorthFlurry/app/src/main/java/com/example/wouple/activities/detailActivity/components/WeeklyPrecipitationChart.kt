package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.wouple.R
import com.example.wouple.elements.CustomPrecipitationBarChart
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import java.time.LocalDate

@Composable
fun WeeklyPrecipitationChart(temp: TemperatureResponse) {
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
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(16.dp)
    ) {
        WeeklyShowersChartView(temp = temp)
    }
}

@Composable
fun WeeklyShowersChartView(temp: TemperatureResponse) {
    val context = LocalContext.current
    val precipitationSum = temp.daily.precipitation_sum.take(7)
    val maxRainSum = precipitationSum.maxOrNull()?.toFloat() ?: return
    val daysOfWeek = (0 until 7).map {
        LocalDate.now().plusDays(it.toLong()).dayOfWeek.name.substring(0, 3)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Precipitation_For_Upcoming_Days),
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            color = Whitehis
        )
        Spacer(modifier = Modifier.weight(1f))
        PopUpViewForPrecipitation(temp)
        val minSumForShowingGraph =
            if (PrecipitationUnitPref.getPrecipitationUnit(context) == PrecipitationUnit.MM) 0.1 else 0.01
        if (maxRainSum <= minSumForShowingGraph) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.No_Precipitation_expected_for_the_week),
                fontWeight = FontWeight.Light,
                fontSize = 15.sp,
                color = Spiro
            )
        } else {
            Row(
                modifier = Modifier
                    .height(170.dp)
                    .drawBehind {
                        // draws X-Axis
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height)
                        )
                    },
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                precipitationSum.forEach { value ->
                    CustomPrecipitationBarChart(size = value.toFloat(), max = maxRainSum)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                daysOfWeek.forEach { label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .width(10.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopUpViewForPrecipitation(temp: TemperatureResponse) {
    var popupVisible by remember { mutableStateOf(false) }
    if (popupVisible) {
        PopUpContent(
            title = stringResource(id = R.string.Weekly_precipitation_chart),
            text = stringResource(id = R.string.Explainer_For_Precipitation),
            onDismiss = { popupVisible = false },
            temp = temp
        )
    }
    IconButton(onClick = { popupVisible = true }) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Show Popup",
            tint = Color.White
        )
    }
}
