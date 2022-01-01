package com.tatsuki.core.usecase

import com.tatsuki.core.repository.DeviceRepository
import com.tatsuki.data.api.Result
import javax.inject.Inject

class SendAirConditionerCommandUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
) {

    suspend fun execute(
        deviceId: String,
        temperature: Int,
        isCool: Boolean,
        isOn: Boolean
    ) {
        val result = deviceRepository
            .sendAirConditionerCommend(deviceId, temperature, isCool, isOn)
        when (result) {
            is Result.ClientError -> {}
            is Result.Error -> {}
            Result.NetworkError -> {}
            Result.ServerError -> {}
            is Result.Success -> {}
        }
    }
}