package com.tatsuki.core.usecase

import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.openweather.response.toCurrentWeatherEntity
import javax.inject.Inject

class FetchCurrentWeatherUseCase @Inject constructor(
    val currentWeatherView: ICurrentWeatherView,
    private val weatherRepository: WeatherRepository
) {
    suspend fun execute(
        lat: Double,
        lon: Double
    ) {
        when (val currentWeatherResult = weatherRepository.forcedUpdateCurrentWeather(lat, lon)) {
            is Result.ClientError -> {
            }
            is Result.Error -> {
            }
            Result.NetworkError -> {
            }
            Result.ServerError -> {
            }
            is Result.Success ->
                currentWeatherView.showCurrentWeather(currentWeatherResult.data.current.toCurrentWeatherEntity())
        }
    }
}