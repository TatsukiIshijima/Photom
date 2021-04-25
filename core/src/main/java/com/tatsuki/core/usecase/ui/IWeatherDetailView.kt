package com.tatsuki.core.usecase.ui

import com.tatsuki.core.entity.DailyWeatherEntity
import com.tatsuki.core.entity.TimelyWeatherEntity

interface IWeatherDetailView {
    fun showTimelyWeather(list: List<TimelyWeatherEntity>)
    fun showDailyWeather(list: List<DailyWeatherEntity>)
}