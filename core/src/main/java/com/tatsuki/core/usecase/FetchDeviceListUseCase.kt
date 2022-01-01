package com.tatsuki.core.usecase

import com.tatsuki.core.repository.DeviceRepository
import com.tatsuki.core.usecase.ui.IDeviceListView
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.switchbot.devicelist.response.toEntity
import javax.inject.Inject

class FetchDeviceListUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val errorView: IErrorView,
    val deviceListView: IDeviceListView,
    private val deviceRepository: DeviceRepository,
) {

    suspend fun execute() {
        loadingView.showLoading()

        val result = deviceRepository.fetchDeviceList()

        loadingView.hideLoading()

        when (result) {
            is Result.ClientError -> {}
            is Result.Error -> {}
            Result.NetworkError -> {}
            Result.ServerError -> {}
            is Result.Success -> {
                result.data?.let {
                    val localDeviceList = it.deviceList
                        .map { device -> device.toEntity() }
                    val remoteDeviceList = it.infraredRemoteList
                        .map { device -> device.toEntity() }
                    deviceListView.showDeviceList(localDeviceList, remoteDeviceList)
                }
            }
        }
    }
}