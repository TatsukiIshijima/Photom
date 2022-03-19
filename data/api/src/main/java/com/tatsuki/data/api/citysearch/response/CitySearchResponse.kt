package com.tatsuki.data.api.citysearch.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CitySearchResponse(

    @Json(name = "status")
    val status: String,

    @Json(name = "data")
    val data: List<CityResponse>
)