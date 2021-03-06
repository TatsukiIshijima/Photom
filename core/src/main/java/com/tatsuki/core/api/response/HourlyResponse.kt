package com.tatsuki.core.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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