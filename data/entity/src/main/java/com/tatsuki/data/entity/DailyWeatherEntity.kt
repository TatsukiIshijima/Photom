package com.tatsuki.data.entity

data class DailyWeatherEntity(
    val date: String?,
    val day: String?,
    val iconUrl: String?,
    val maxTemperature: Int,
    val minTemperature: Int
)