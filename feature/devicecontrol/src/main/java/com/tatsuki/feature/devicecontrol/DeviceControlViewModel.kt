package com.tatsuki.feature.devicecontrol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchDeviceListUseCase
import com.tatsuki.core.usecase.FetchSensorDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceControlViewModel @Inject constructor(
    private val fetchSensorDataUseCase: FetchSensorDataUseCase,
    private val fetchDeviceListUseCase: FetchDeviceListUseCase,
) : ViewModel() {

    val loadingFlow = fetchDeviceListUseCase
        .loadingView
        .state
        .mutableLoadingFlow
        .asStateFlow()

    val localDeviceListFlow = fetchDeviceListUseCase
        .deviceListView
        .state
        .mutableLocalDeviceListFlow
        .asStateFlow()

    val remoteDeviceListFlow = fetchDeviceListUseCase
        .deviceListView
        .state
        .mutableRemoteDeviceListFlow
        .asStateFlow()

    val sensorDataFlow = fetchSensorDataUseCase
        .sensorDataView
        .state
        .mutableSensorDataFlow
        .asStateFlow()

    fun fetchSensorData() {
        viewModelScope.launch {
            fetchSensorDataUseCase.execute()
        }
    }

    fun fetchDeviceList() {
        viewModelScope.launch {
            fetchDeviceListUseCase.execute()
        }
    }
}