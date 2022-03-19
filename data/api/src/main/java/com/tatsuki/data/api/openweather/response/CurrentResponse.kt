package com.tatsuki.data.api.openweather.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.CurrentWeatherEntity
import com.tatsuki.data.entity.CurrentWeatherInfoItem

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
    val windSpeed: Double,

    @Json(name = "weather")
    val weather: List<WeatherResponse>
)

fun CurrentResponse.toCurrentWeatherEntity(): CurrentWeatherEntity {
    val icon = weather.firstOrNull()?.icon
    return CurrentWeatherEntity(
        temp.toInt(),
        weather.firstOrNull()?.main,
        if (icon.isNullOrEmpty()) ""
        else "http://openweathermap.org/img/wn/${icon}@2x.png"
    )
}

fun CurrentResponse.toDetailItems(): List<CurrentWeatherInfoItem> {
    val humidityItem = CurrentWeatherInfoItem.HumidityItem(humidity)
    val pressureItem = CurrentWeatherInfoItem.PressureItem(pressure)
    val windSpeedItem = CurrentWeatherInfoItem.WindSpeedItem(windSpeed)
    return listOf(humidityItem, pressureItem, windSpeedItem)
}