package com.example.wouple

import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.OpenMeteoLocationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DateFormatterTest {

    @Test
    fun testFormatDate_validDateTime_returnsHour() {
        val formatted = DateFormatter.formatDate("2026-09-08T14:30")
        assertEquals("14", formatted)
    }

    @Test
    fun testFormatDate_invalidDateTime_returnsEmpty() {
        val formatted = DateFormatter.formatDate("invalid-date")
        assertEquals("", formatted)
    }

    @Test
    fun testSafeZoneId_validZone_returnsZone() {
        val zone = DateFormatter.safeZoneId("Europe/London")
        assertEquals("Europe/London", zone.id)
    }

    @Test
    fun testSafeZoneId_autoOrInvalid_returnsDefaultZone() {
        val zoneAuto = DateFormatter.safeZoneId("auto")
        assertNotNull(zoneAuto)

        val zoneNull = DateFormatter.safeZoneId(null)
        assertNotNull(zoneNull)

        val zoneInvalid = DateFormatter.safeZoneId("Invalid/Timezone_Name")
        assertNotNull(zoneInvalid)
    }

    @Test
    fun testOpenMeteoLocationResult_toSearchedLocation() {
        val openMeteoLoc = OpenMeteoLocationResult(
            name = "Oslo",
            latitude = 59.9139,
            longitude = 10.7522,
            country = "Norway",
            admin1 = "Oslo"
        )
        val searched = openMeteoLoc.toSearchedLocation()
        assertEquals("59.9139", searched.lat)
        assertEquals("10.7522", searched.lon)
        assertEquals("Oslo, Norway", searched.display_name)
    }
}
