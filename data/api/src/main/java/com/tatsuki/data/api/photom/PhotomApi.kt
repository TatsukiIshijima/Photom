package com.tatsuki.data.api.photom

import com.tatsuki.data.api.photom.photo.response.PhotoListResponse
import com.tatsuki.data.api.photom.sensor.response.SensorResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface PhotomApi {

    @Headers("Content-Type: application/json")
    @GET("photo/list")
    suspend fun getPhotoList(): PhotoListResponse

    @Headers("Content-Type: application/json")
    @GET("sensor")
    suspend fun getSensor(): SensorResponse
}