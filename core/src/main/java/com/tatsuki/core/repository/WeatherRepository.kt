package com.tatsuki.core.repository

import com.tatsuki.core.State
import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.openweather.OpenWeatherApi
import com.tatsuki.data.api.openweather.response.OneCallResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    @Deprecated("instead of fetchCurrentWeather")
    fun getWeather(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        lat: Double,
        lon: Double
    ): Flow<State<OneCallResponse>> {
        return flow {
            try {
                val response = openWeatherApi.getOneCall(lat, lon)
                _cache = response
                emit(State.success(response))
            } catch (e: Exception) {
                emit(State.failed<OneCallResponse>(e))
            }
        }.flowOn(dispatcher)
    }
}