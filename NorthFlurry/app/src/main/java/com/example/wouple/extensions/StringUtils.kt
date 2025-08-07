package com.example.wouple.extensions

fun getProperDisplayName(displayName: String?): String? {
    return displayName?.split(",")?.firstOrNull()
}