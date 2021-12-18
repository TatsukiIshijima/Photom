package com.tatsuki.data.api.switchbot.devicelist.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.DeviceEntity
import com.tatsuki.data.entity.DeviceType

@JsonClass(generateAdapter = true)
data class InfraredRemoteResponse(

    @Json(name = "deviceId")
    val deviceId: String,

    @Json(name = "deviceName")
    val deviceName: String,

    @Json(name = "remoteType")
    val remoteType: String,

    @Json(name = "hubDeviceId")
    val hubDeviceId: String
)

fun InfraredRemoteResponse.toEntity(): DeviceEntity {
    val type = when (remoteType) {
        "Fan" -> DeviceType.Fan
        "Air Conditioner" -> DeviceType.AirConditioner
        "Light" -> DeviceType.Light
        else -> null
    }
    return DeviceEntity(deviceId, deviceName, type)
}
