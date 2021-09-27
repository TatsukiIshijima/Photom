package com.tatsuki.data.api

import com.tatsuki.data.api.response.OneCallResponse

class OpenWeatherApiClient(): ApiClient(url = "https://api.openweathermap.org/data/2.5/") {

    private val oneCallApi: OpenWeatherApiInterface =
        retrofit.create(OpenWeatherApiInterface::class.java)

    suspend fun getOneCall(lat: Double, lon: Double): OneCallResponse = oneCallApi.getOneCall(lat, lon)
}