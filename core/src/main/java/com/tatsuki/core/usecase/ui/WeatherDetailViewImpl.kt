package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition
import javax.inject.Inject

class WeatherDetailViewImpl @Inject constructor(
) : IWeatherDetailView {

    override fun showPlace(name: String) {

    }

    override fun showCurrentWeather(condition: WeatherCondition, temp: Int) {

    }

    override fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>) {

    }

    override fun showTimelyWeather(list: List<TimelyWeatherEntity>) {

    }

    override fun showDailyWeather(list: List<DailyWeatherEntity>) {

    }
}