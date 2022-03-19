package com.tatsuki.datasource

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.tatsuki.data.entity.AddressEntity
import com.tatsuki.data.entity.GeoLocationEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class Location(
    val geoLocationEntity: GeoLocationEntity = GeoLocationEntity(lat = 139.697723, lon = 35.66367),
    val prefecture: AddressEntity.Prefecture = AddressEntity.Prefecture.Tokyo,
    val city: AddressEntity.City = AddressEntity.City(code = "13113", name = "渋谷区")
)

object LocationSerializer : Serializer<Location> {
    override val defaultValue: Location = Location()

    override suspend fun readFrom(input: InputStream): Location {
        try {
            return Json.decodeFromString(
                Location.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read Location", e)
        }
    }

    override suspend fun writeTo(t: Location, output: OutputStream) {
        output.write(Json.encodeToString(Location.serializer(), t).encodeToByteArray())
    }

}