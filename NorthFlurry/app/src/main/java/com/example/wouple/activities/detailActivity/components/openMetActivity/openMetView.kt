package com.example.wouple.activities.detailActivity.components.openMetActivity

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import androidx.core.net.toUri

@Composable
fun OpenMeteorologyView(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF3F54BE)
        val lighterShades = listOf(
            baseColor,
            baseColor.copy(alpha = 0.9f),
            baseColor.copy(alpha = 0.8f),
            baseColor.copy(alpha = 0.5f),
        )
        lighterShades
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
        )
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(brush = Brush.verticalGradient(background)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(
            text = stringResource(id = R.string.Attribution),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.White
        )
        Text(
            text = stringResource(id = R.string.open_meteo_attribution_part1) + "\n" +
                    stringResource(id = R.string.open_meteo_attribution_part2) + "\n" +
                    stringResource(id = R.string.open_meteo_attribution_part3),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 8.dp),
            color = Color.White,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
        Text(
            text = stringResource(id = R.string.open_meteo_visit),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .clickable {
                    val url = "https://open-meteo.com/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = url.toUri()
                    context.startActivity(intent)
                }
        )
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp),
            text = stringResource(id = R.string.open_meteo_licenceInfo),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.White
        )
        Text(
            text = stringResource(id = R.string.weather_data_ccBy_licence),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 8.dp),
            color = Color.White,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
        val annotatedString = with(AnnotatedString.Builder()) {
            append(stringResource(id = R.string.cc_by_4_license_details))
            toAnnotatedString()
        }
        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                textDecoration = TextDecoration.Underline
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = "https://creativecommons.org/licenses/by/4.0/".toUri()
                context.startActivity(intent)
            }
        )
        Text(
            text = stringResource(id = R.string.DataAccuracyDisclaimer),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        Text(
            text = stringResource(id = R.string.weather_data_disclaimer),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 8.dp),
            color = Color.White,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
    }
}
