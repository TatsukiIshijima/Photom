package com.tatsuki.core.entity

data class TimelyWeatherEntity(
    val time: String,
    val iconUrl: String,
    val temperature: String
)