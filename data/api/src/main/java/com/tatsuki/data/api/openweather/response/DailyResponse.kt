package com.tatsuki.data.api.openweather.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.extension.format
import com.tatsuki.data.extension.toDate

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

fun DailyResponse.toDailyWeatherEntity(): DailyWeatherEntity {
    val icon = weather.firstOrNull()?.icon
    return DailyWeatherEntity(
        time.toDate().format("M/dd"),
        time.toDate().format("E曜日"),
        if (icon.isNullOrEmpty()) ""
        else "http://openweathermap.org/img/wn/${icon}@2x.png",
        temp.max.toInt(),
        temp.min.toInt()
    )
}