package com.tatsuki.data.api

import com.tatsuki.data.api.response.OneCallResponse
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
        @Query("appid") appId: String = Const.OpenWeatherAPIKey
    ): OneCallResponse
}