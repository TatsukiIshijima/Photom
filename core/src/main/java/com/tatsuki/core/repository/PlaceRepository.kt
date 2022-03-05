package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.addresssearch.AddressSearchApi
import com.tatsuki.data.api.addresssearch.response.AddressSearchResponse
import com.tatsuki.data.api.citysearch.CitySearchApi
import com.tatsuki.data.api.citysearch.response.CitySearchResponse
import com.tatsuki.data.entity.LocationEntity
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val addressSearchApi: AddressSearchApi,
    private val citySearchApi: CitySearchApi
) {

    suspend fun fetchAddress(locationName: String): Result<List<AddressSearchResponse>> =
        ApiClient.safeApiCall({ addressSearchApi.getAddress(locationName) })

    suspend fun fetchCity(prefecture: LocationEntity.Prefecture): Result<CitySearchResponse> {
        return ApiClient.safeApiCall({ citySearchApi.getCity(prefecture.code) })
    }
}