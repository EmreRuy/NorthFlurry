package com.example.wouple.activities.lottieCopyRightActivity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun LottieView(temp: TemperatureResponse) {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(brush = Brush.verticalGradient(background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val currentContext = LocalContext.current
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(
            text = stringResource(id = R.string.Attribution),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(16.dp),
            text = stringResource(id = R.string.LottieFilesCopyright),
            fontWeight = FontWeight.Light,
            color = Color.White,
            fontSize = 14.sp
        )
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Text(
                text = stringResource(id = R.string.LottieMoreInfo),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 4.dp),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = stringResource(id = R.string.LottieLicense),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable {
                        val url = "https://lottiefiles.com/page/license"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        currentContext.startActivity(intent)
                    }
            )
        }

    }
}