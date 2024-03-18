package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.vintage

@Composable
fun NoTemperatureView(
    onStartButtonClicked: () -> Unit
) {
    val background =
        listOf(
            // Color(0xFF4067DD),
            // Color(0xFF4067DD),
          /*  Color(0xFF3D52BB),
            Color(0xFF3D52BB),
            Whitehis */
            Color(0xFF3D52BB),
            Color(0xFF3D52BB)
        )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = background,
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_moon_cloud),
            contentDescription = "cloud",
            alignment = Center,
            modifier = Modifier.size(150.dp)
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "NorthFlurry",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 32.sp,
            color = vintage,
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "Weather Forecast",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.SansSerif,
            fontSize = 32.sp,
            color = Corn,
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            text = "the ultimate weather companion designed to make your daily life easier. Our app delivers accurate, real-time weather forecasts, ensuring you're prepared for whatever Mother Nature has in store.",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 16.sp,
            color = Whitehis.copy(alpha = 0.8f),
        )
        Button(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(vintage),
            onClick = {
              onStartButtonClicked()
            }
        ) {
            Text(
                text = "Get Started"
            )
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}