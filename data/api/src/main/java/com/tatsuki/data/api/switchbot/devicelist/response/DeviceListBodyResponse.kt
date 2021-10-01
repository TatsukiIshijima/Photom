package com.tatsuki.data.api.switchbot.devicelist.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceListBodyResponse(

    @Json(name = "deviceList")
    val deviceList: List<DeviceResponse>,

    @Json(name = "infraredRemoteList")
    val infraredRemoteList: List<InfraredRemoteResponse>
)
