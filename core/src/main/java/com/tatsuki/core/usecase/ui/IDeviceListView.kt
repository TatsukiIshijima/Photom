package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.DeviceEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface IDeviceListView {

    interface IState {
        val mutableLocalDeviceListFlow: MutableStateFlow<List<DeviceEntity>>
        val mutableRemoteDeviceListFlow: MutableStateFlow<List<DeviceEntity>>
    }

    val state: IState

    fun showDeviceList(
        localDeviceList: List<DeviceEntity>,
        remoteDeviceList: List<DeviceEntity>
    )
}