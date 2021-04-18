package com.tatsuki.core.entity

data class DailyWeatherEntity(
    val date: String,
    val day: String,
    val iconUrl: String,
    val maxTemperature: String,
    val minTemperature: String
)