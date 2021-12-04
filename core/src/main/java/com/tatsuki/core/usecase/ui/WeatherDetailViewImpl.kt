package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class WeatherDetailViewImpl @Inject constructor(
) : IWeatherDetailView {

    class State : IWeatherDetailView.IState {
        override val mutablePlaceFlow = MutableStateFlow("")
        override val mutableConditionFlow = MutableStateFlow<WeatherCondition?>(null)
        override val mutableTemperatureFlow = MutableStateFlow<Int?>(null)
        override val mutableWeatherInfoListFlow =
            MutableStateFlow<List<CurrentWeatherInfoItem>>(listOf())
        override val mutableTimelyWeatherFlow =
            MutableStateFlow<List<TimelyWeatherEntity>>(listOf())
        override val mutableDailyWeatherFlow = MutableStateFlow<List<DailyWeatherEntity>>(listOf())
    }

    override val state: IWeatherDetailView.IState = State()

    override fun showPlace(name: String) {
        state.mutablePlaceFlow.value = name
    }

    override fun showCurrentWeather(condition: WeatherCondition, temp: Int) {
        state.mutableConditionFlow.value = condition
        state.mutableTemperatureFlow.value = temp
    }

    override fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>) {
        state.mutableWeatherInfoListFlow.value = list
    }

    override fun showTimelyWeather(list: List<TimelyWeatherEntity>) {
        state.mutableTimelyWeatherFlow.value = list
    }

    override fun showDailyWeather(list: List<DailyWeatherEntity>) {
        state.mutableDailyWeatherFlow.value = list
    }
}