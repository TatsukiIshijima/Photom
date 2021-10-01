package com.tatsuki.data.api.photom.photo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoResponse(

    @Json(name = "id")
    val id: Int,

    @Json(name = "url")
    val url: String
)
