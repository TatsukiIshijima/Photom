package com.tatsuki.core.api

import com.tatsuki.core.Consts
import com.tatsuki.core.api.response.OneCallResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiInterface {

    // https://openweathermap.org/api/one-call-api#current
    @GET("onecall")
    suspend fun getOneCall(@Query("lat") lat: Double,
                   @Query("lon") lon: Double,
                   @Query("lang") lang: String = "ja",
                   @Query("appid") appId: String = Consts.OpenWeatherAPIKey
    ): OneCallResponse
}