package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface ICurrentWeatherView {

    interface IState {
        val mutableCurrentWeatherFlow: MutableStateFlow<CurrentWeatherEntity?>
    }

    val state: IState

    // 現在の天気、温度の表示
    fun showCurrentWeather(entity: CurrentWeatherEntity)
}