package com.tatsuki.data.api.openweather.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.extension.format
import com.tatsuki.data.extension.toDate

@JsonClass(generateAdapter = true)
data class HourlyResponse(

    @Json(name = "dt")
    val time: Int,

    @Json(name = "temp")
    val temp: Double,

    @Json(name = "pressure")
    val pressure: Int,

    @Json(name = "humidity")
    val humidity: Int,

    @Json(name = "weather")
    val weather: List<WeatherResponse>,

    @Json(name = "pop")
    val pop: Double
)

fun HourlyResponse.toTimelyWeatherEntity(): TimelyWeatherEntity =
    TimelyWeatherEntity(
        time.toDate().format("kk:mm"),
        weather.firstOrNull()?.icon,
        temp.toInt()
    )