package com.tatsuki.data.entity

sealed class CurrentWeatherInfoItem {
    data class PressureItem(val value: Int) : CurrentWeatherInfoItem()
    data class HumidityItem(val value: Int) : CurrentWeatherInfoItem()
    data class WindSpeedItem(val value: Double) : CurrentWeatherInfoItem()
}
