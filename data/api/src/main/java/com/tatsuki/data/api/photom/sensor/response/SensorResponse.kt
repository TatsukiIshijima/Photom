package com.tatsuki.data.api.photom.sensor.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SensorResponse(

    @Json(name = "temp")
    val temp: Float,

    @Json(name = "pressure")
    val pressure: Float,

    @Json(name = "humidity")
    val humidity: Float,

    @Json(name = "lux")
    val lux: Float
)
