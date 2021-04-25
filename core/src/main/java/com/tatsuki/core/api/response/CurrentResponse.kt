package com.tatsuki.core.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.core.entity.CurrentWeatherEntity
import com.tatsuki.core.entity.CurrentWeatherInfoItem

@JsonClass(generateAdapter = true)
data class CurrentResponse(

    @Json(name = "dt")
    val currentTime: Int,

    @Json(name = "temp")
    val temp: Double,

    @Json(name = "pressure")
    val pressure: Int,

    @Json(name = "humidity")
    val humidity: Int,

    @Json(name = "wind_speed")
    val windSpeed: Int,

    @Json(name = "weather")
    val weather: List<WeatherResponse>
)

fun CurrentResponse.toCurrentWeatherEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        temp.toInt(),
        weather.firstOrNull()?.main,
        weather.firstOrNull()?.icon
    )
}

fun CurrentResponse.toDetailItems(): List<CurrentWeatherInfoItem> {
    val humidityItem = CurrentWeatherInfoItem.HumidityItem(humidity)
    val pressureItem = CurrentWeatherInfoItem.PressureItem(pressure)
    val windSpeedItem = CurrentWeatherInfoItem.WindSpeedItem(windSpeed)
    return listOf(humidityItem, pressureItem, windSpeedItem)
}