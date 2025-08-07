package com.example.wouple

import com.example.wouple.extensions.getProperDisplayName
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetProperDisplayNameTest {

    @Test
    fun testWithComma() {
        val input = "Paris, Île-de-France"
        val result = getProperDisplayName(input)
        assertEquals("Paris", result)
    }

    @Test
    fun testWithoutComma() {
        val input = "Berlin"
        val result = getProperDisplayName(input)
        assertEquals("Berlin", result)
    }

    @Test
    fun testWithNull() {
        val input: String? = null
        val result = getProperDisplayName(input)
        assertEquals(null, result)
    }

    @Test
    fun testWithEmptyString() {
        val input = ""
        val result = getProperDisplayName(input)
        assertEquals("", result)
    }
}