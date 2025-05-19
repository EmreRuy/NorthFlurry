package com.example.wouple.activities.detailActivity.components

import android.content.res.Configuration
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.CustomBarChart
import com.example.wouple.model.api.TemperatureResponse
import java.time.LocalDate

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Day Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Night Mode")
@Composable
fun PreviewUvIndexChart() {

    MaterialTheme {
        UvIndexChart(temp = TemperatureResponse.getMockInstance())
    }
}


@Composable
fun UvIndexChart(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp))
            .padding(12.dp),
    ) {
        UvChartView(temp = temp)
    }
}

@Composable
fun UvChartView(temp: TemperatureResponse) {
    val dailyUv =
        temp.daily.uv_index_max.take(7)
    val maxUv = dailyUv.maxOrNull()?.toFloat() ?: 0f
    val daysOfWeek = (0 until 7).map {
        getLocalizedDayName(LocalDate.now().plusDays(it.toLong()).dayOfWeek).substring(0, 3)
    }
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.UV_Index_For_Upcoming_Days),
            fontWeight = FontWeight.SemiBold,
            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        PopUpView()
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
            dailyUv.forEach { value ->
                CustomBarChart(size = value.toFloat(), max = maxUv)
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
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
