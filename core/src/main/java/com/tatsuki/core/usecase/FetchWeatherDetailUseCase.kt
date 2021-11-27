package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.core.usecase.ui.IWeatherDetailView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.addresssearch.response.toAddressEntity
import com.tatsuki.data.api.flatMap
import com.tatsuki.data.api.openweather.response.toDailyWeatherEntity
import com.tatsuki.data.api.openweather.response.toDetailItems
import com.tatsuki.data.api.openweather.response.toTimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition
import javax.inject.Inject

class FetchWeatherDetailUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val errorView: IErrorView,
    val weatherView: IWeatherDetailView,
    private val placeRepository: PlaceRepository,
    private val weatherRepository: WeatherRepository
) {
    // TODO:ResultをReturnするようにすれば綺麗になるかも
    suspend fun execute(
        locationName: String
    ) {
        loadingView.showLoading()

        val result = placeRepository.fetchAddress(locationName)
            .flatMap {
                val coordinates = it.first().toAddressEntity()
                weatherRepository.fetchCurrentWeather(coordinates.lat, coordinates.lon)
            }

        loadingView.hideLoading()

        when (result) {
            is Result.ClientError -> {}
            is Result.Error -> {}
            Result.NetworkError -> {}
            Result.ServerError -> {}
            is Result.Success -> {
                val data = result.data
                val weather = data.current.weather.firstOrNull() ?: return
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
                val detailInfoList = data.current.toDetailItems()
                val timelyWeatherList = data.hourly.map { it.toTimelyWeatherEntity() }
                val dailyWeatherList = data.daily.map { it.toDailyWeatherEntity() }

                weatherView.showCurrentWeather(condition, data.current.temp.toInt())
                weatherView.showCurrentWeatherDetail(detailInfoList)
                weatherView.showTimelyWeather(timelyWeatherList)
                weatherView.showDailyWeather(dailyWeatherList)
            }
        }
    }
}