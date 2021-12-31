package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.addresssearch.AddressSearchApi
import com.tatsuki.data.api.addresssearch.response.AddressSearchResponse
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val addressSearchApi: AddressSearchApi
) {

    suspend fun fetchAddress(locationName: String): Result<List<AddressSearchResponse>> =
        ApiClient.safeApiCall({ addressSearchApi.getAddress(locationName) })
}