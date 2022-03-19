package com.tatsuki.data.api.switchbot.devicelist.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.entity.DeviceEntity
import com.tatsuki.data.entity.DeviceType

@JsonClass(generateAdapter = true)
data class DeviceResponse(

    @Json(name = "deviceId")
    val deviceId: String,

    @Json(name = "deviceName")
    val deviceName: String,

    @Json(name = "deviceType")
    val deviceType: String,

    @Json(name = "hubDeviceId")
    val hubDeviceId: String
)

fun DeviceResponse.toEntity(): DeviceEntity {
    val type = when (deviceType) {
        "Air Conditioner" -> DeviceType.AirConditioner
        "DIY Fan" -> DeviceType.Fan
        "Hub Mini" -> DeviceType.HubMini
        "Light" -> DeviceType.Light
        else -> null
    }
    return DeviceEntity(deviceId, deviceName, type)
}
