package com.tatsuki.datasource

import android.content.Context
import androidx.datastore.dataStore
import com.tatsuki.data.entity.AddressEntity
import com.tatsuki.data.entity.GeoLocationEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationDataSource {

    private val Context.dataStore by dataStore(JSON_FILE_NAME, LocationSerializer)

    override suspend fun getGeoLocation(): GeoLocationEntity {
        return context.dataStore.data.first().geoLocationEntity
    }

    override suspend fun setGeoLocation(geoLocationEntity: GeoLocationEntity) {
        context.dataStore.updateData {
            it.copy(geoLocationEntity = geoLocationEntity)
        }
    }

    override suspend fun getPrefecture(): AddressEntity.Prefecture {
        return context.dataStore.data.first().prefecture
    }

    override suspend fun setPrefecture(prefecture: AddressEntity.Prefecture) {
        context.dataStore.updateData {
            it.copy(prefecture = prefecture)
        }
    }

    override suspend fun getCity(): AddressEntity.City {
        return context.dataStore.data.first().city
    }

    override suspend fun setCity(city: AddressEntity.City) {
        context.dataStore.updateData {
            it.copy(city = city)
        }
    }

    companion object {
        private const val JSON_FILE_NAME = "location.json"
    }
}