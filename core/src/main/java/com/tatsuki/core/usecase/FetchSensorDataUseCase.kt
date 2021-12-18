package com.tatsuki.core.usecase

import com.tatsuki.core.repository.SensorRepository
import com.tatsuki.core.usecase.ui.ISensorDataView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.photom.sensor.response.toEntity
import javax.inject.Inject

class FetchSensorDataUseCase @Inject constructor(
    val sensorDataView: ISensorDataView,
    private val sensorRepository: SensorRepository
) {
    suspend fun execute() {
        when (val result = sensorRepository.fetchSensorData()) {
            is Result.Success -> {
                sensorDataView.showSensorData(result.data.toEntity())
            }
            else -> {
                sensorDataView.showSensorData(null)
            }
        }
    }
}