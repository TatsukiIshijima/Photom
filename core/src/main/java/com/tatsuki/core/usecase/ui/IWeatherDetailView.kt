package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition
import kotlinx.coroutines.flow.MutableStateFlow

interface IWeatherDetailView {

    interface IState {
        val mutablePlaceFlow: MutableStateFlow<String>
        val mutableConditionFlow: MutableStateFlow<WeatherCondition?>
        val mutableTemperatureFlow: MutableStateFlow<Int?>
        val mutableWeatherInfoListFlow: MutableStateFlow<List<CurrentWeatherInfoItem>>
        val mutableTimelyWeatherFlow: MutableStateFlow<List<TimelyWeatherEntity>>
        val mutableDailyWeatherFlow: MutableStateFlow<List<DailyWeatherEntity>>
    }

    val state: IState

    fun showPlace(name: String)
    fun showCurrentWeather(condition: WeatherCondition, temp: Int)
    fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>)
    fun showTimelyWeather(list: List<TimelyWeatherEntity>)
    fun showDailyWeather(list: List<DailyWeatherEntity>)
}