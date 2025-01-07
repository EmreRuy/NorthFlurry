package com.example.wouple.activities.settingsActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.wouple.ui.theme.beige

@Composable
fun SettingsCardTwo() {
    Spacer(modifier = Modifier.padding(top = 12.dp))
    Text(
        text = stringResource(id = R.string.UnitSettings),
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Medium,
        color = beige.copy(alpha = 0.8f),
        fontSize = 28.sp
    )
}

@Composable
fun TemperatureUnitSettings(temp: TemperatureResponse, viewModel: SettingsViewModel) {
    val temperatureUnits = TemperatureUnit.values()
    val currentUnit by viewModel.temperatureUnit.collectAsState()

    var selectedUnitIndex by remember {
        mutableIntStateOf(temperatureUnits.indexOf(currentUnit))
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(id = R.string.TemperatureUnits),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            textAlign = TextAlign.Start
        )

        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                viewModel.updateTemperatureUnit(temperatureUnits[index])
                selectedUnitIndex = index
            },
            units = temperatureUnits.map { it.toString() },
            temp = temp
        )
    }
}

@Composable
fun PrecipitationUnitSettings(temp: TemperatureResponse) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.PrecipitationUnits),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start
        )
        val units = PrecipitationUnit.values()
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
            },
            units = units.map { it.toString() },
            temp = temp
        )
    }
}

@Composable
fun WindUnitSettings(temp: TemperatureResponse) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.WindSpeedUnits),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start
        )
        val units = WindUnit.values()
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
            },
            units = units.map { it.toString() },
            temp = temp
        )
    }
}