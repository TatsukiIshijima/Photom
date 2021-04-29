package com.tatsuki.core.usecase.ui

import com.tatsuki.core.entity.CurrentWeatherInfoItem
import com.tatsuki.core.entity.DailyWeatherEntity
import com.tatsuki.core.entity.TimelyWeatherEntity
import com.tatsuki.core.entity.WeatherCondition

interface IWeatherDetailView {
    fun showPlace(name: String)
    fun showCurrentWeather(condition: WeatherCondition, temp: Int)
    fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>)
    fun showTimelyWeather(list: List<TimelyWeatherEntity>)
    fun showDailyWeather(list: List<DailyWeatherEntity>)
}