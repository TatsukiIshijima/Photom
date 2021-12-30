package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.flatMap
import com.tatsuki.data.api.switchbot.SwitchBotApi
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
}