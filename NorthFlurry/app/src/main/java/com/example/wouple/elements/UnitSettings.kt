package com.example.wouple.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun UnitSettings(
    selectedUnitIndex: Int,
    onUnitSelected: (index: Int) -> Unit,
    units: List<String>,
    modifier: Modifier = Modifier,
    temp: TemperatureResponse
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        UnitTab(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = onUnitSelected,
            units = units,
            temp = temp
        )
    }
}

@Composable
fun UnitTab(
    selectedUnitIndex: Int,
    onUnitSelected: (index: Int) -> Unit,
    units: List<String>,
    temp: TemperatureResponse
) {
    CustomTabForSettings(
        selectedItemIndex = selectedUnitIndex,
        items = units,
        onClick = { index ->
            onUnitSelected(index)
        },
        temp = temp
    )
}