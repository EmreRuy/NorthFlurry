package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        // Locations search
        GetSearchBarAndList(
            locations = locations,
            onSearch = onSearch,
            searchedLocation = searchedLocation,
            onLocationButtonClicked = onLocationButtonClicked,
            onClose = onClose
        )
        // Location name with degrees
        GetLocationAndDegree(
            temp = temp,
            searchedLocation = searchedLocation,
        )
        // Horizontal wave animation
        GetHorizontalWaveView()
        // Bottom view
        GetBottomView(searchedLocation = searchedLocation, temp = temp)

        searchedLocation.value?.let {
            GetAttributionForOpenMet(searchedLocation = it)
        }
    }
}
