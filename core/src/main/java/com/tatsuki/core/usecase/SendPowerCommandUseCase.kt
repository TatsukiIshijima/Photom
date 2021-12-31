package com.tatsuki.core.usecase

import com.tatsuki.core.repository.DeviceRepository
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.data.api.Result
import javax.inject.Inject

class SendPowerCommandUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val errorView: IErrorView,
    private val deviceRepository: DeviceRepository,
) {

    suspend fun execute(deviceId: String, isOn: Boolean) {
        loadingView.showLoading()

        val result = deviceRepository.sendPowerCommand(deviceId, isOn)

        loadingView.hideLoading()

        when (result) {
            is Result.ClientError -> {}
            is Result.Error -> {}
            Result.NetworkError -> {}
            Result.ServerError -> {}
            is Result.Success -> {}
        }
    }
}