package com.tatsuki.data.api.switchbot.devicelist.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
