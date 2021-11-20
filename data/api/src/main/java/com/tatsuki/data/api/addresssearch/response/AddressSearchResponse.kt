package com.tatsuki.data.api.addresssearch.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressSearchResponse(

    @Json(name = "geometry")
    val geometry: GeometryResponse
)
