package com.tatsuki.core.usecase

import com.tatsuki.core.IErrorView
import com.tatsuki.core.ILoadingView
import com.tatsuki.core.State
import com.tatsuki.core.api.response.toCurrentWeatherEntity
import com.tatsuki.core.entity.CurrentWeatherEntity
import com.tatsuki.core.repository.WeatherRepository
import kotlinx.coroutines.flow.collect

class FetchCurrentWeatherUseCase(
    private val currentWeatherView: ICurrentWeatherView,
    private val weatherRepository: WeatherRepository
) {

    companion object {
        private val TAG = FetchCurrentWeatherUseCase::class.java.simpleName
    }

    suspend fun execute(lat: Double, lon: Double) {

        currentWeatherView.showLoading()

        weatherRepository.getWeather(lat = lat, lon = lon)
            .collect { state ->
                when (state) {
                    is State.Success -> {
                        currentWeatherView
                            .showCurrentWeather(state.data.current.toCurrentWeatherEntity())
                    }
                    is State.Failed -> {
                        currentWeatherView.showError(state.exception)
                    }
                }
            }

        currentWeatherView.hideLoading()
    }
}

interface ICurrentWeatherView: ILoadingView, IErrorView {
    // 現在の天気、温度の表示
    fun showCurrentWeather(entity: CurrentWeatherEntity)
}