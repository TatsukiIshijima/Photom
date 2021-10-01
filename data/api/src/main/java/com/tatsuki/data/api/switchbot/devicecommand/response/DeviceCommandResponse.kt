package com.tatsuki.data.api.switchbot.devicecommand.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatsuki.data.api.switchbot.SwitchBotResponse

@JsonClass(generateAdapter = true)
data class DeviceCommandResponse(

    @Json(name = "statusCode")
    override val statusCode: Int,

    @Json(name = "message")
    override val message: String,

    @Json(name = "body")
    override val body: String?

) : SwitchBotResponse<String?>
