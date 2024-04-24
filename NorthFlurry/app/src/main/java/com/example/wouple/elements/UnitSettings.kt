package com.example.wouple.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        elevation = 4.dp
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

/*
@Composable
fun CustomTemperatureUnitTab(
    selectedUnitIndex: Int,
    onUnitSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    temp: TemperatureResponse
) {
    val tabItems = listOf("Celsius", "Fahrenheit")

    CustomTabForSettings(
        selectedItemIndex = selectedUnitIndex,
        items = tabItems,
        modifier = modifier,
        temp = temp,
        onClick = { index ->
            onUnitSelected(index)
        }
    )
} */