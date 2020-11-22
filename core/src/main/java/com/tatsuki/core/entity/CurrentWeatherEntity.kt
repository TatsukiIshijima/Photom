package com.tatsuki.core.entity

data class CurrentWeatherEntity(
    val temp: Int,
    val state: String?,
    val icon: String?
)