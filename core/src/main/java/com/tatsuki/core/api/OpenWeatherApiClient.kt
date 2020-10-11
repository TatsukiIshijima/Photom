package com.tatsuki.core.api

import com.tatsuki.core.api.response.OneCallResponse

class OpenWeatherApiClient(): ApiClient(url = "https://api.openweathermap.org/data/2.5/") {

    private val oneCallApi: OpenWeatherApiInterface =
        retrofit.create(OpenWeatherApiInterface::class.java)

    suspend fun getOneCall(lat: Double, lon: Double): OneCallResponse = oneCallApi.getOneCall(lat, lon)
}