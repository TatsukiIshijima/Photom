package com.tatsuki.data.api.switchbot

import com.tatsuki.data.api.switchbot.devicecommand.request.DeviceCommandRequest
import com.tatsuki.data.api.switchbot.devicecommand.response.DeviceCommandResponse
import com.tatsuki.data.api.switchbot.devicelist.response.DeviceListResponse
import retrofit2.http.*

interface SwitchBotApi {

    // https://github.com/OpenWonderLabs/SwitchBotAPI
    @GET("devices")
    suspend fun getDeviceList(
        @Header("Authorization") token: String
    ): DeviceListResponse

    @Headers("Content-Type: application/json; charset=utf8")
    @POST("devices/{deviceId}/commands")
    suspend fun postDeviceCommand(
        @Path("deviceId") deviceId: String,
        @Body deviceCommandRequest: DeviceCommandRequest
    ): DeviceCommandResponse
}