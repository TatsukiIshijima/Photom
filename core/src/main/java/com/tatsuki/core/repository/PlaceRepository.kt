package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.addresssearch.AddressSearchApi
import com.tatsuki.data.api.addresssearch.response.AddressSearchResponse
import com.tatsuki.data.api.citysearch.CitySearchApi
import com.tatsuki.data.api.citysearch.response.CitySearchResponse
import com.tatsuki.data.entity.AddressEntity
import com.tatsuki.data.entity.GeoLocationEntity
import com.tatsuki.datasource.LocationDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val addressSearchApi: AddressSearchApi,
    private val citySearchApi: CitySearchApi,
    private val locationDataSource: LocationDataSource,
) {

    var prefectureCash: AddressEntity.Prefecture? = null
        private set
    var cityCash: AddressEntity.City? = null
        private set

    suspend fun fetchAddress(locationName: String): Result<List<AddressSearchResponse>> =
        ApiClient.safeApiCall({ addressSearchApi.getAddress(locationName) })

    suspend fun fetchCity(prefecture: AddressEntity.Prefecture): Result<CitySearchResponse> {
        return ApiClient.safeApiCall({ citySearchApi.getCity(prefecture.code) })
    }

    suspend fun getGeoLocation(): GeoLocationEntity {
        return locationDataSource.getGeoLocation()
    }

    suspend fun saveGeoLocation(geoLocationEntity: GeoLocationEntity) {
        locationDataSource.setGeoLocation(geoLocationEntity)
    }

    fun cashPrefecture(prefecture: AddressEntity.Prefecture) {
        prefectureCash = prefecture
    }

    suspend fun getPrefecture(): AddressEntity.Prefecture {
        return locationDataSource.getPrefecture()
    }

    suspend fun savePrefecture(prefecture: AddressEntity.Prefecture) {
        locationDataSource.setPrefecture(prefecture)
    }

    fun cashCity(city: AddressEntity.City) {
        cityCash = city
    }

    suspend fun getCity(): AddressEntity.City {
        return locationDataSource.getCity()
    }

    suspend fun saveCity(city: AddressEntity.City) {
        locationDataSource.setCity(city)
    }
}