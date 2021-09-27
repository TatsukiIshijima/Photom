package com.tatsuki.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TempResponse(

    @Json(name = "min")
    val min: Double,

    @Json(name = "max")
    val max: Double
)