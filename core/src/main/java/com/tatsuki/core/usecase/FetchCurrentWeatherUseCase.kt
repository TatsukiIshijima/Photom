package com.tatsuki.core.usecase

import com.tatsuki.core.State
import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.data.api.openweather.response.toCurrentWeatherEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FetchCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val placeRepository: PlaceRepository
) {

    @FlowPreview
    suspend fun execute(view: ICurrentWeatherView) {

        view.showLoading()

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
                    view.hideLoading()
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
                        view
                            .showCurrentWeather(it.data.current.toCurrentWeatherEntity())
                    }
                    is State.Failed -> {
                        view.showError(it.exception)
                    }
                }
                view.hideLoading()
            }
    }
}