package com.tatsuki.data.api.addresssearch.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.GeoLocationEntity

@JsonClass(generateAdapter = true)
data class AddressSearchResponse(

    @Json(name = "geometry")
    val geometry: GeometryResponse
)

fun AddressSearchResponse.toGeoLocationEntity(): GeoLocationEntity =
    GeoLocationEntity(geometry.coordinates[1], geometry.coordinates[0])
