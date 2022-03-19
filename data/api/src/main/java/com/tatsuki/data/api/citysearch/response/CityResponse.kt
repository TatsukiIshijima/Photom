package com.tatsuki.data.api.citysearch.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityResponse(

    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String
)
