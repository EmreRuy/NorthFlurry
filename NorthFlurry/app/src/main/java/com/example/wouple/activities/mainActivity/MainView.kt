package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wouple.activities.detailActivity.components.openMetActivity.GetAttributionForOpenMet
import com.example.wouple.activities.mainActivity.components.GetBottomView
import com.example.wouple.activities.mainActivity.components.GetHorizontalWaveView
import com.example.wouple.activities.mainActivity.components.GetLocationAndDegree
import com.example.wouple.activities.mainActivity.components.GetSearchBarAndList
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun MainView(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        //Top view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(bottom = 18.dp),
            contentAlignment = TopStart
        ) {
            // Location name with degrees
            GetLocationAndDegree(
                temp = temp,
                searchedLocation = searchedLocation,
            )
            // locations search
            GetSearchBarAndList(
                locations = locations,
                onSearch = onSearch,
                searchedLocation = searchedLocation,
                onLocationButtonClicked = onLocationButtonClicked,
                onClose = onClose
            )
        }
        //Bottom view
        GetBottomView(searchedLocation = searchedLocation, temp = temp)
        searchedLocation.value?.let {
            GetAttributionForOpenMet(searchedLocation = it)
        }
        GetHorizontalWaveView()
    }
}