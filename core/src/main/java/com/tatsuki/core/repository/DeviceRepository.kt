package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.flatMap
import com.tatsuki.data.api.switchbot.SwitchBotApi
import com.tatsuki.data.api.switchbot.devicecommand.request.DeviceCommandRequest
import com.tatsuki.data.api.switchbot.devicecommand.response.DeviceCommandResponse
import com.tatsuki.data.api.switchbot.devicelist.response.DeviceListBodyResponse
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val switchBotApi: SwitchBotApi
) {

    suspend fun fetchDeviceList(): Result<DeviceListBodyResponse?> {
        val result = ApiClient.safeApiCall({ switchBotApi.getDeviceList() })
        return result
            .flatMap {
                if (it.statusCode == 100) Result.Success(it.body)
                else Result.Error(it.statusCode, it.message)
            }
    }

    suspend fun sendPowerCommand(deviceId: String, isOn: Boolean): Result<DeviceCommandResponse> {
        val result = ApiClient.safeApiCall({
            switchBotApi.postDeviceCommand(
                deviceId,
                DeviceCommandRequest(
                    if (isOn) "turnOn" else "turnOff",
                    "default",
                    if (isOn) "" else "command"
                )
            )
        })
        return result
            .flatMap {
                if (it.statusCode == 100) Result.Success(it)
                else Result.Error(it.statusCode, it.message)
            }
    }

    suspend fun sendAirConditionerCommend(
        deviceId: String,
        temperature: Int,
        isCool: Boolean,
        isOn: Boolean
    ): Result<DeviceCommandResponse> {
        val mode = if (isCool) 2 else 5
        val powerState = if (isOn) "on" else "off"
        val parameter = "${temperature},${mode},1,${powerState}"
        val result = ApiClient.safeApiCall({
            switchBotApi.postDeviceCommand(
                deviceId,
                DeviceCommandRequest("setAll", parameter, "command")
            )
        })
        return result
            .flatMap {
                if (it.statusCode == 100) Result.Success(it)
                else Result.Error(it.statusCode, it.message)
            }
    }
}