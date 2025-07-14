package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.wouple.activities.detailActivity.components.openMetActivity.GetAttributionForOpenMet
import com.example.wouple.activities.mainActivity.components.GetBottomView
import com.example.wouple.activities.mainActivity.components.GetHorizontalWaveView
import com.example.wouple.activities.mainActivity.components.GetLocationAndDegree
import com.example.wouple.activities.mainActivity.components.GetSearchBarAndList
import com.example.wouple.elements.SearchBar
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
    val isSearchExpanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Search bar (This part was already correct)
            SearchBar(
                isSearchExpanded = isSearchExpanded,
                onSearch = onSearch,
                onClose = onClose
            )

            // Only shows when not searching
            if (!isSearchExpanded.value) {
                GetLocationAndDegree(temp = temp, searchedLocation = searchedLocation)
                GetHorizontalWaveView()
                GetBottomView(searchedLocation = searchedLocation, temp = temp)

                searchedLocation.value?.let {
                    GetAttributionForOpenMet(searchedLocation = it)
                }
            }
        }

        if (isSearchExpanded.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f))
            ) {
                GetSearchBarAndList(
                    locations = locations,
                    onSearch = onSearch,
                    searchedLocation = searchedLocation,
                    onLocationButtonClicked = {
                        onLocationButtonClicked(it)
                        isSearchExpanded.value = false
                    },
                    onClose = {
                        isSearchExpanded.value = false
                        onClose()
                    },
                    isSearchExpanded = isSearchExpanded
                )
            }
        }
    }
}

