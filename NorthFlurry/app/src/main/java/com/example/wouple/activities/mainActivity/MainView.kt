package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            // Canvas with rounded bottom
            val color = MaterialTheme.colorScheme.background
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .align(Alignment.BottomStart)
            ) {
                val width = size.width
                val height = size.height
                val curveHeight = 150f // Deeper curve for a more pronounced rounded bottom

                // Draw the rounded bottom shape
                drawPath(
                    path = Path().apply {
                        moveTo(0f, 0f) // Start at top-left corner
                        lineTo(0f, height - curveHeight) // Left side
                        quadraticTo(
                            x1 = width / 2f,
                            y1 = height + curveHeight,
                            x2 = width,
                            y2 = height - curveHeight
                        )
                        lineTo(width, 0f) // Line back to the top-right corner
                        close() // Close the path
                    },
                    color = color
                )
            }

            // Location name with degrees
            GetLocationAndDegree(
                temp = temp,
                searchedLocation = searchedLocation,
            )

            // Locations search
            GetSearchBarAndList(
                locations = locations,
                onSearch = onSearch,
                searchedLocation = searchedLocation,
                onLocationButtonClicked = onLocationButtonClicked,
                onClose = onClose
            )
        }

        // Bottom view
        GetBottomView(searchedLocation = searchedLocation, temp = temp)

        searchedLocation.value?.let {
            GetAttributionForOpenMet(searchedLocation = it)
        }

        // Horizontal wave animation
        GetHorizontalWaveView()
    }
}
