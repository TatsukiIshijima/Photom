package com.tatsuki.data.api.switchbot.devicelist.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
