package com.tatsuki.data.api.switchbot

import com.tatsuki.data.api.BuildConfig
import com.tatsuki.data.api.switchbot.devicecommand.request.DeviceCommandRequest
import com.tatsuki.data.api.switchbot.devicecommand.response.DeviceCommandResponse
import com.tatsuki.data.api.switchbot.devicelist.response.DeviceListResponse
import retrofit2.http.*

interface SwitchBotApi {

    // https://github.com/OpenWonderLabs/SwitchBotAPI
    @Headers("Authorization: ${BuildConfig.SWITCH_BOT_API_TOKEN}")
    @GET("devices")
    suspend fun getDeviceList(): DeviceListResponse

    @Headers(
        "Content-Type: application/json; charset=utf8",
        "Authorization: ${BuildConfig.SWITCH_BOT_API_TOKEN}"
    )
    @POST("devices/{deviceId}/commands")
    suspend fun postDeviceCommand(
        @Path("deviceId") deviceId: String,
        @Body deviceCommandRequest: DeviceCommandRequest
    ): DeviceCommandResponse
}