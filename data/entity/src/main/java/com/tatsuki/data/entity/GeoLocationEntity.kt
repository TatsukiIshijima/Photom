package com.tatsuki.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class GeoLocationEntity(
    val lat: Double,
    val lon: Double
)
