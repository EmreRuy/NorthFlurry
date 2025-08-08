package com.example.wouple

import androidx.compose.ui.graphics.Color
import com.example.wouple.activities.detailActivity.utils.getAirQualityInfo
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AirQualityInfoTest {
    @Test
    fun testAqi_0_to_20_returnsGood(){
        val result = getAirQualityInfo(15)
        assertEquals(R.string.good, result.descriptionResId)
        assertEquals(Color(0xFF4CAF50), result.color)

    }
    @Test
    fun testAqi_21_to_40_returnsFair(){
        val result = getAirQualityInfo(35)
        assertEquals(R.string.fair, result.descriptionResId)
        assertEquals(Color(0xFF8BC34A), result.color)
    }
    @Test
    fun testAqi_41_to_60_returnsModerate(){
        val result = getAirQualityInfo(55)
        assertEquals(R.string.moderate, result.descriptionResId)
        assertEquals(Color(0xFFFFB700), result.color)
    }
    @Test
    fun testAqi_61_to_80_returnsPoor(){
        val result = getAirQualityInfo(70)
        assertEquals(R.string.poor, result.descriptionResId)
        assertEquals(Color(0xFFFF8400), result.color)
    }
    @Test
    fun testAqi_81_to_100_returnsVeryPoor(){
        val result = getAirQualityInfo(90)
        assertEquals(R.string.very_poor, result.descriptionResId)
        assertEquals(Color(0xFFF44336), result.color)
    }
    @Test
    fun testAqi_101_to_max_returnsHazardous(){
        val result = getAirQualityInfo(105)
        assertEquals(R.string.hazardous, result.descriptionResId)
        assertEquals(Color(0xFF9C27B0), result.color)
    }
    @Test
    fun testAqi_below_zero_returnsUnknown(){
        val result = getAirQualityInfo(-10)
        assertEquals(R.string.unknown, result.descriptionResId)
        assertEquals(Color(0xFF9E9E9E), result.color)
    }
}