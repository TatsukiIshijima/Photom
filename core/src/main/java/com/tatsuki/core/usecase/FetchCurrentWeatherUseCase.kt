package com.tatsuki.core.usecase

import com.tatsuki.core.State
import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.data.api.response.toCurrentWeatherEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class FetchCurrentWeatherUseCase(
    private val currentWeatherView: ICurrentWeatherView,
    private val weatherRepository: WeatherRepository,
    private val placeRepository: PlaceRepository
) {

    companion object {
        private val TAG = FetchCurrentWeatherUseCase::class.java.simpleName
    }

    @FlowPreview
    suspend fun execute() {

        currentWeatherView.showLoading()

        // FIXME:もう少しきれいにつなげたい
        placeRepository.fetchPlace()
            .map {
                when (it) {
                    is State.Failed -> null
                    is State.Success -> it.data
                }
            }
            .filter {
                if (it == null) {
                    currentWeatherView.hideLoading()
                    return@filter false
                }
                return@filter true
            }
            .filterNotNull()
            .flatMapConcat {
                weatherRepository.getWeather(
                    lat = it.location.latitude,
                    lon = it.location.longitude
                )
            }
            .collect {
                when (it) {
                    is State.Success -> {
                        currentWeatherView
                            .showCurrentWeather(it.data.current.toCurrentWeatherEntity())
                    }
                    is State.Failed -> {
                        currentWeatherView.showError(it.exception)
                    }
                }
                currentWeatherView.hideLoading()
            }
    }
}