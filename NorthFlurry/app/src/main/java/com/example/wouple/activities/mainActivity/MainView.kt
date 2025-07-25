package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
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
        if (!isSearchExpanded.value) {
            val canvasColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(320.dp)
                    .align(Alignment.TopCenter)
            ) {
                val width = size.width
                val height = size.height
                val curveHeight = 150f

                drawPath(
                    path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(0f, height - curveHeight)
                        quadraticTo(
                            x1 = width / 2f,
                            y1 = height + curveHeight,
                            x2 = width,
                            y2 = height - curveHeight
                        )
                        lineTo(width, 0f)
                        close()
                    },
                    color = canvasColor
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // These composable will NOT scroll

            // Search Bar is always fixed at the very top
            SearchBar(
                isSearchExpanded = isSearchExpanded,
                onSearch = onSearch,
                onClose = onClose
            )

            if (!isSearchExpanded.value) {
                GetLocationAndDegree(
                    temp = temp,
                    searchedLocation = searchedLocation,
                )
            }

            if (!isSearchExpanded.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    GetBottomView(searchedLocation = searchedLocation, temp = temp)

                    searchedLocation.value?.let {
                        GetAttributionForOpenMet(searchedLocation = it)
                    }
                    GetHorizontalWaveView()
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

