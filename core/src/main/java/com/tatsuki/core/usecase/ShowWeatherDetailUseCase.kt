package com.tatsuki.core.usecase

import com.tatsuki.core.api.response.toDailyWeatherEntity
import com.tatsuki.core.api.response.toDetailItems
import com.tatsuki.core.api.response.toTimelyWeatherEntity
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.IWeatherDetailView

class ShowWeatherDetailUseCase(
    private val weatherDetailView: IWeatherDetailView,
    private val weatherRepository: WeatherRepository
) {

    fun execute() {
        weatherRepository.cache?.let {
            it.current.weather.firstOrNull()?.let { weather ->
                weatherDetailView.showCurrentWeather(weather.id, it.current.temp.toInt())
            }
            val detailInfoList = it.current.toDetailItems()
            weatherDetailView.showCurrentWeatherDetail(detailInfoList)
            val timelyWeatherList = it.hourly.map { weather ->
                weather.toTimelyWeatherEntity()
            }
            weatherDetailView.showTimelyWeather(timelyWeatherList)
            val dailyWeatherList = it.daily.map { weather ->
                weather.toDailyWeatherEntity()
            }
            weatherDetailView.showDailyWeather(dailyWeatherList)
        }
    }
}