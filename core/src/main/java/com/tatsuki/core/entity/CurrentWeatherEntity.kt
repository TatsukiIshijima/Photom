package com.tatsuki.core.entity

data class CurrentWeatherEntity(
    val temp: Double,
    val state: String?,
    val icon: String?
)