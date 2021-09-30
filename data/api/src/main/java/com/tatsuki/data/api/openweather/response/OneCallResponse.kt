package com.tatsuki.data.api.openweather.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OneCallResponse(

    @Json(name = "lat")
    val latitude: Double,

    @Json(name = "lon")
    val longitude: Double,

    @Json(name = "timezone")
    val timezone: String,

    @Json(name = "timezone_offset")
    val timezoneOffset: Double,

    @Json(name = "current")
    val current: CurrentResponse,

    @Json(name = "hourly")
    val hourly: List<HourlyResponse>,

    @Json(name = "daily")
    val daily: List<DailyResponse>
)