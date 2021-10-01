package com.tatsuki.data.api.switchbot.devicecommand.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceCommandRequest(

    @Json(name = "command")
    val command: String,

    @Json(name = "parameter")
    val parameter: String,

    @Json(name = "commandType")
    val commandType: String
)
