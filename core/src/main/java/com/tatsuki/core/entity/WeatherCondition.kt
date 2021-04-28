package com.tatsuki.core.entity

sealed class WeatherCondition {
    data class Thunderstorm(val id: Int) : WeatherCondition()
    data class Drizzle(val id: Int) : WeatherCondition()
    data class Rain(val id: Int) : WeatherCondition()
    data class Snow(val id: Int) : WeatherCondition()
    data class Atmosphere(val id: Int) : WeatherCondition()
    data class Clear(val id: Int) : WeatherCondition()
    data class Cloud(val id: Int) : WeatherCondition()
}
