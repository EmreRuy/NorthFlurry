package com.example.wouple.activities.settingsActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.SettingsViewModel
import com.example.wouple.elements.UnitSettings
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref

@Composable
fun SettingsCardTwo() {
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(
        text = stringResource(id = R.string.UnitSettings),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        fontSize = 22.sp
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun TemperatureUnitSettings(
    viewModel: SettingsViewModel,
    onChanged: () -> Unit
) {
    val temperatureUnits = TemperatureUnit.entries.toTypedArray()
    val currentUnit by viewModel.temperatureUnit.collectAsState()
    val context = LocalContext.current

    var selectedUnitIndex by remember {
        mutableIntStateOf(temperatureUnits.indexOf(currentUnit))
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(id = R.string.TemperatureUnits),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            textAlign = TextAlign.Start
        )

        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                TemperatureUnitPref.setTemperatureUnit(context, temperatureUnits[index])
                viewModel.updateTemperatureUnit(temperatureUnits[index])
                selectedUnitIndex = index
                onChanged()
            },
            units = temperatureUnits.map { it.toString() }
        )
    }
}

@Composable
fun PrecipitationUnitSettings(onChanged: () -> Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.PrecipitationUnits),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start
        )
        val units = PrecipitationUnit.entries.toTypedArray()
        var selectedUnitIndex by remember {
            mutableIntStateOf(
                units.indexOf(
                    PrecipitationUnitPref.getPrecipitationUnit(
                        context
                    )
                )
            )
        }
        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                PrecipitationUnitPref.setPrecipitationUnit(context, units[index])
                selectedUnitIndex = index
                onChanged()
            },
            units = units.map { it.toString() }
        )
    }
}

@Composable
fun WindUnitSettings(onChanged: () -> Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.WindSpeedUnits),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start
        )
        val units = WindUnit.entries.toTypedArray()
        var selectedUnitIndex by remember {
            mutableIntStateOf(
                units.indexOf(
                    WindUnitPref.getWindUnit(
                        context
                    )
                )
            )
        }
        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                WindUnitPref.setWindUnit(context, units[index])
                selectedUnitIndex = index
                onChanged()
            },
            units = units.map { it.toString() }
        )
    }
}