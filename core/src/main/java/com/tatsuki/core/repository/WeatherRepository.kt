package com.tatsuki.core.repository

import com.tatsuki.core.State
import com.tatsuki.core.api.OpenWeatherApiClient
import com.tatsuki.core.api.response.OneCallResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiClient: OpenWeatherApiClient
) {

    companion object {
        private val TAG = WeatherRepository::class.java.simpleName
    }

    fun getWeather(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        lat: Double,
        lon: Double
    ) : Flow<State<OneCallResponse>> {
        return flow {
            try {
                val response = apiClient.getOneCall(lat, lon)
                emit(State.success(response))
            } catch (e: Exception) {
                emit(State.failed<OneCallResponse>(e))
            }
        }.flowOn(dispatcher)
    }
}