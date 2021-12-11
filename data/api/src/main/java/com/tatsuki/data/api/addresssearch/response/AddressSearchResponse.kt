package com.tatsuki.data.api.addresssearch.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.AddressEntity

@JsonClass(generateAdapter = true)
data class AddressSearchResponse(

    @Json(name = "geometry")
    val geometry: GeometryResponse
)

fun AddressSearchResponse.toAddressEntity(): AddressEntity =
    AddressEntity(geometry.coordinates[1], geometry.coordinates[0])
