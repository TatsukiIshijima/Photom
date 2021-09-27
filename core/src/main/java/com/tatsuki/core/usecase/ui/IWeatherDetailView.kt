package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition

interface IWeatherDetailView {
    fun showPlace(name: String)
    fun showCurrentWeather(condition: WeatherCondition, temp: Int)
    fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>)
    fun showTimelyWeather(list: List<TimelyWeatherEntity>)
    fun showDailyWeather(list: List<DailyWeatherEntity>)
}