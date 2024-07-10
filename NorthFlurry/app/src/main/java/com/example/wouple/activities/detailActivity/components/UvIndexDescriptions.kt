package com.example.wouple.activities.detailActivity.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.wouple.R

@Composable
fun getUvIndexDescription(uvIndex: Int): String {
    return when (uvIndex) {
        in 0..2 -> stringResource(id = R.string.uv_index_low)
        in 3..5 -> stringResource(id = R.string.uv_index_moderate)
        in 6..7 -> stringResource(id = R.string.uv_index_high)
        in 8..10 -> stringResource(id = R.string.uv_index_very_high)
        in 10..Int.MAX_VALUE -> stringResource(id = R.string.uv_index_extreme)
        else -> stringResource(id = R.string.unknown)
    }
}