package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.SensorEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface ISensorDataView {

    interface IState {
        val mutableSensorDataFlow: MutableStateFlow<SensorEntity?>
    }

    val state: IState

    fun showSensorData(entity: SensorEntity?)
}