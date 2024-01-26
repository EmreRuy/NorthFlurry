package com.example.wouple.formatter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun formatDate(value: String): String{
        val localDateTime = LocalDateTime.parse(value)
        val dateTimeFormatter = DateTimeFormatter.ofPattern("HH")
        return localDateTime.format(dateTimeFormatter)
    }
}