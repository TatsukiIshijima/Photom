package com.tatsuki.data.api.photom.photo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoListResponse(

    @Json(name = "photos")
    val photos: List<PhotoResponse>
)
