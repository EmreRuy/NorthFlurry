package com.example.wouple.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wouple.ui.theme.Card

@Composable
fun UnitSettings(
    selectedUnitIndex: Int,
    onUnitSelected: (index: Int) -> Unit,
    units: List<String>,
    modifier: Modifier = Modifier
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
            units = units
        )
    }
}

@Composable
fun UnitTab(
    selectedUnitIndex: Int,
    onUnitSelected: (index: Int) -> Unit,
    units: List<String>
) {
    CustomTabForSettings(
        selectedItemIndex = selectedUnitIndex,
        items = units,
        onClick = { index ->
            onUnitSelected(index)
        }
    )
}