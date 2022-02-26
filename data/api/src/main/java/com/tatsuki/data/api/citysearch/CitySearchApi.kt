package com.tatsuki.data.api.citysearch

import com.tatsuki.data.api.citysearch.response.CitySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CitySearchApi {

    @GET("CitySearch")
    suspend fun getCity(
        @Query("area") area: String
    ): CitySearchResponse
}