package com.tatsuki.data.api.photom.photo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.PhotoEntity
import com.tatsuki.data.entity.PhotoListEntity

@JsonClass(generateAdapter = true)
data class PhotoListResponse(

    @Json(name = "photos")
    val photos: List<PhotoResponse>
)

fun PhotoListResponse.toPhotoListEntity(): PhotoListEntity =
    PhotoListEntity(photos.map { PhotoEntity(it.id, it.url) })