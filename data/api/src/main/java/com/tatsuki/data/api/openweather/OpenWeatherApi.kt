package com.tatsuki.data.api.openweather

import com.tatsuki.data.api.BuildConfig
import com.tatsuki.data.api.openweather.response.OneCallResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    // https://openweathermap.org/api/one-call-api#current
    @GET("onecall")
    suspend fun getOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "ja",
        @Query("exclude") exclude: String = "minutely",
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): OneCallResponse
}