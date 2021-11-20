package com.tatsuki.data.api.addresssearch.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeometryResponse(

    @Json(name = "coordinates")
    val coordinates: List<Double>,

    @Json(name = "type")
    val type: String,
)
