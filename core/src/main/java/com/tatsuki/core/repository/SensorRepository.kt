package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.photom.PhotomApi
import com.tatsuki.data.api.photom.sensor.response.SensorResponse
import javax.inject.Inject

class SensorRepository @Inject constructor(
    private val photomApi: PhotomApi
) {

    suspend fun fetchSensorData(): Result<SensorResponse> =
        ApiClient.safeApiCall({ photomApi.getSensor() })
}