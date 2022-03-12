package com.tatsuki.datasource

import com.tatsuki.data.entity.AddressEntity
import com.tatsuki.data.entity.GeoLocationEntity

interface LocationDataSource {

    suspend fun getGeoLocation(): GeoLocationEntity

    suspend fun setGeoLocation(geoLocationEntity: GeoLocationEntity)

    suspend fun getPrefecture(): AddressEntity.Prefecture

    suspend fun setPrefecture(prefecture: AddressEntity.Prefecture)

    suspend fun getCity(): AddressEntity.City

    suspend fun setCity(city: AddressEntity.City)

}