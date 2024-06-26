package com.example.wouple.activities.detailActivity.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.wouple.R
import java.time.DayOfWeek

@Composable
fun getLocalizedDayName(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> stringResource(id = R.string.monday)
        DayOfWeek.TUESDAY -> stringResource(id = R.string.tuesday)
        DayOfWeek.WEDNESDAY -> stringResource(id = R.string.wednesday)
        DayOfWeek.THURSDAY -> stringResource(id = R.string.thursday)
        DayOfWeek.FRIDAY -> stringResource(id = R.string.friday)
        DayOfWeek.SATURDAY -> stringResource(id = R.string.saturday)
        DayOfWeek.SUNDAY -> stringResource(id = R.string.sunday)
    }
}