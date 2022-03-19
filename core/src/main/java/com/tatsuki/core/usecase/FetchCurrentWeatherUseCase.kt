package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.openweather.response.toCurrentWeatherEntity
import javax.inject.Inject

class FetchCurrentWeatherUseCase @Inject constructor(
    val currentWeatherView: ICurrentWeatherView,
    private val placeRepository: PlaceRepository,
    private val weatherRepository: WeatherRepository,
) {
    suspend fun execute() {
        val geoLocation = placeRepository.getGeoLocation()
        val result = weatherRepository.forcedUpdateCurrentWeather(geoLocation.lat, geoLocation.lon)
        when (result) {
            is Result.ClientError -> {
            }
            is Result.Error -> {
            }
            Result.NetworkError -> {
            }
            Result.ServerError -> {
            }
            is Result.Success ->
                currentWeatherView.showCurrentWeather(result.data.current.toCurrentWeatherEntity())
        }
    }
}