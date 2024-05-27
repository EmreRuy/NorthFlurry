package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay

@Composable
fun GetBottomView(
    searchedLocation: MutableState<SearchedLocation?>,
    temp: TemperatureResponse
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        searchedLocation.value?.let { GetSevenHoursForecast(temp) }
        GetSevenDaysForecast(temp)
    }
}