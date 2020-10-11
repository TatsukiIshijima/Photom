package com.tatsuki.core.repository

import com.tatsuki.core.api.OpenWeatherApiClient
import com.tatsuki.core.api.response.OneCallResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepository(
    private val apiClient: OpenWeatherApiClient
) {

    companion object {
        private val TAG = WeatherRepository::class.java.simpleName
    }

    suspend fun getWeather(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        lat: Double,
        lon: Double
    ) : Flow<OneCallResponse> {
        return flow {
            try {
                val response = apiClient.getOneCall(lat, lon)
                emit(response)
            } catch (e: Exception) {
                throw Exception(e)
            }
        }.flowOn(dispatcher)
    }
}