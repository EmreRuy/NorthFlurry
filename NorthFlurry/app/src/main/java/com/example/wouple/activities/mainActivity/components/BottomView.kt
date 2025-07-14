package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse


@Composable
fun GetBottomView(
    searchedLocation: MutableState<SearchedLocation?>,
    temp: TemperatureResponse,
) {
    Column(
        modifier = Modifier
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        searchedLocation.value?.let { GetSevenHoursForecast(temp) }
        GetSevenDaysForecast(temp)
    }
}