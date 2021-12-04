package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CurrentWeatherViewImpl @Inject constructor(
) : ICurrentWeatherView {

    class State : ICurrentWeatherView.IState {
        override val mutableCurrentWeatherFlow = MutableStateFlow<CurrentWeatherEntity?>(null)
    }

    override val state: ICurrentWeatherView.IState = State()

    override fun showCurrentWeather(entity: CurrentWeatherEntity) {
        state.mutableCurrentWeatherFlow.value = entity
    }
}