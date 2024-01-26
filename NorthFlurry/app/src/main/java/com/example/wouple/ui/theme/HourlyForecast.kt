package com.example.wouple.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.wouple.R

@Composable
fun HourlyForecastDisplay(
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "13:00",
        color = Color.LightGray)
        
        Image(painter = painterResource(id = R.drawable.cloudy2) , contentDescription = null )
        
        Text(text = "7",
        color = Color.LightGray,
        fontWeight = FontWeight.Bold)

    }

}