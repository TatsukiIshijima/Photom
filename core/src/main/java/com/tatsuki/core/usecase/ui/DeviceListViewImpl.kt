package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.DeviceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DeviceListViewImpl @Inject constructor(
) : IDeviceListView {

    class State : IDeviceListView.IState {
        override val mutableLocalDeviceListFlow = MutableStateFlow(listOf<DeviceEntity>())
        override val mutableRemoteDeviceListFlow = MutableStateFlow(listOf<DeviceEntity>())
    }

    override val state: IDeviceListView.IState = State()

    override fun showDeviceList(
        localDeviceList: List<DeviceEntity>,
        remoteDeviceList: List<DeviceEntity>
    ) {
        state.mutableLocalDeviceListFlow.value = localDeviceList
        state.mutableRemoteDeviceListFlow.value = remoteDeviceList
    }
}