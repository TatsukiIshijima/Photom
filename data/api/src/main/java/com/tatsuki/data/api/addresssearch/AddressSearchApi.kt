package com.tatsuki.data.api.addresssearch

import com.tatsuki.data.api.addresssearch.response.AddressSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressSearchApi {

    @GET("AddressSearch")
    suspend fun getAddress(
        @Query("q") locationName: String
    ): List<AddressSearchResponse>
}