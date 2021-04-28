package com.tatsuki.core.usecase

import com.tatsuki.core.api.response.toDailyWeatherEntity
import com.tatsuki.core.api.response.toDetailItems
import com.tatsuki.core.api.response.toTimelyWeatherEntity
import com.tatsuki.core.entity.WeatherCondition
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.IWeatherDetailView

class ShowWeatherDetailUseCase(
    private val weatherDetailView: IWeatherDetailView,
    private val weatherRepository: WeatherRepository
) {

    fun execute() {
        weatherRepository.cache?.let {
            it.current.weather.firstOrNull()?.let { weather ->
                val condition =
                    when (weather.id) {
                        200, 201, 202, 210, 211, 212, 221, 230, 231, 232 -> WeatherCondition.Thunderstorm(
                            weather.id
                        )
                        300, 301, 302, 310, 311, 312, 313, 314, 321 -> WeatherCondition.Drizzle(
                            weather.id
                        )
                        500, 501, 502, 503, 504, 511, 520, 521, 522, 531 -> WeatherCondition.Rain(
                            weather.id
                        )
                        600, 601, 602, 611, 612, 613, 615, 616, 620, 621, 622 -> WeatherCondition.Snow(
                            weather.id
                        )
                        701, 711, 721, 731, 741, 751, 761, 762, 771, 781 -> WeatherCondition.Atmosphere(
                            weather.id
                        )
                        801, 802, 803, 804 -> WeatherCondition.Cloud(weather.id)
                        else -> WeatherCondition.Clear(weather.id)
                    }
                weatherDetailView.showCurrentWeather(condition, it.current.temp.toInt())
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