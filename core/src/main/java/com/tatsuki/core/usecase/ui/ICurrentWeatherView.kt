package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherEntity

interface ICurrentWeatherView: ILoadingView, IErrorView {
    // 現在の天気、温度の表示
    fun showCurrentWeather(entity: CurrentWeatherEntity)
}