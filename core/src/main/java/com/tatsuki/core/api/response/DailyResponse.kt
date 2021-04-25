package com.tatsuki.core.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.core.entity.DailyWeatherEntity
import com.tatsuki.core.extension.format
import com.tatsuki.core.extension.toDate

@JsonClass(generateAdapter = true)
data class DailyResponse(

    @Json(name = "dt")
    val time: Int,

    @Json(name = "temp")
    val temp: TempResponse,

    @Json(name = "pressure")
    val pressure: Int,

    @Json(name = "humidity")
    val humidity: Int,

    @Json(name = "weather")
    val weather: List<WeatherResponse>,

    @Json(name = "pop")
    val pop: Double
)

fun DailyResponse.toDailyWeatherEntity(): DailyWeatherEntity =
    DailyWeatherEntity(
        time.toDate().format("M/dd"),
        time.toDate().format("E曜日"),
        weather.firstOrNull()?.icon,
        temp.max.toInt(),
        temp.min.toInt()
    )