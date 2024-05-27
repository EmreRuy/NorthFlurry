package com.example.wouple.activities.settingsActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.wouple.elements.UnitSettings
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import com.example.wouple.ui.theme.beige
import com.example.wouple.ui.theme.mocassin

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
fun TemperatureUnitSettings(temp: TemperatureResponse) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.TemperatureUnits),
            color = mocassin,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start
        )
        val temperatureUnits = TemperatureUnit.values()
        var selectedUnitIndex by remember {
            mutableStateOf(
                temperatureUnits.indexOf(
                    TemperatureUnitPref.getTemperatureUnit(context)
                )
            )
        }
        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                TemperatureUnitPref.setTemperatureUnit(context, temperatureUnits[index])
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
            color = mocassin,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start
        )
        val units = PrecipitationUnit.values()
        var selectedUnitIndex by remember {
            mutableStateOf(
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
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.WindSpeedUnits),
            color = mocassin,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start
        )
        val units = WindUnit.values()
        var selectedUnitIndex by remember {
            mutableStateOf(
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