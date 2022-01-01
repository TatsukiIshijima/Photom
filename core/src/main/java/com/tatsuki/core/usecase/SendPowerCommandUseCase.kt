package com.tatsuki.core.usecase

import com.tatsuki.core.repository.DeviceRepository
import com.tatsuki.data.api.Result
import javax.inject.Inject

class SendPowerCommandUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
) {

    suspend fun execute(deviceId: String, isOn: Boolean) {
        when (deviceRepository.sendPowerCommand(deviceId, isOn)) {
            is Result.ClientError -> {}
            is Result.Error -> {}
            Result.NetworkError -> {}
            Result.ServerError -> {}
            is Result.Success -> {}
        }
    }
}