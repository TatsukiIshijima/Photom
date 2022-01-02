package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.openweather.OpenWeatherApi
import com.tatsuki.data.api.openweather.response.OneCallResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) {

    private var _cache: OneCallResponse? = null

    suspend fun fetchCurrentWeather(lat: Double, lon: Double): Result<OneCallResponse> {
        if (_cache != null) {
            return Result.Success(_cache!!)
        }
        val result = forcedUpdateCurrentWeather(lat, lon)
        when (result) {
            is Result.Success -> {
                _cache = result.data
            }
            else -> {
            }
        }
        return result
    }

    suspend fun forcedUpdateCurrentWeather(lat: Double, lon: Double): Result<OneCallResponse> =
        ApiClient.safeApiCall({ openWeatherApi.getOneCall(lat, lon) })
}