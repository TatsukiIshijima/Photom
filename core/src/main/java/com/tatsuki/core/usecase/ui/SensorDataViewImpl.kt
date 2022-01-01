package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.SensorEntity
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SensorDataViewImpl @Inject constructor(
) : ISensorDataView {

    class State : ISensorDataView.IState {
        override val mutableSensorDataFlow = MutableStateFlow<SensorEntity?>(null)
    }

    override val state: ISensorDataView.IState = State()

    override fun showSensorData(entity: SensorEntity?) {
        state.mutableSensorDataFlow.value = entity
    }
}