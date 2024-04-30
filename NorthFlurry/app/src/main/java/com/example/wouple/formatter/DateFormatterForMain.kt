package com.example.wouple.formatter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatterForMain {
        fun formatDateMain(value: String): String{
            val localDateTime = LocalDateTime.parse(value)
            val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            return localDateTime.format(dateTimeFormatter)
        }
}