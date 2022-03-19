package com.tatsuki.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchWeatherDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val fetchWeatherDetailUseCase: FetchWeatherDetailUseCase
) : ViewModel() {

    val loadingFlow = fetchWeatherDetailUseCase
        .loadingView
        .state
        .mutableLoadingFlow
        .asStateFlow()

    val placeFlow = fetchWeatherDetailUseCase
        .weatherView
        .state
        .mutablePlaceFlow
        .asStateFlow()

    val conditionFlow = fetchWeatherDetailUseCase
        .weatherView
        .state
        .mutableConditionFlow
        .asStateFlow()

    val temperatureFlow = fetchWeatherDetailUseCase
        .weatherView
        .state
        .mutableTemperatureFlow
        .asStateFlow()

    val weatherInfoListFlow = fetchWeatherDetailUseCase
        .weatherView
        .state
        .mutableWeatherInfoListFlow
        .asStateFlow()

    val timelyWeatherFlow = fetchWeatherDetailUseCase
        .weatherView
        .state
        .mutableTimelyWeatherFlow
        .asStateFlow()

    val dailyWeatherFlow = fetchWeatherDetailUseCase
        .weatherView
        .state
        .mutableDailyWeatherFlow
        .asStateFlow()

    fun fetchWeatherDetail() {
        viewModelScope.launch {
            fetchWeatherDetailUseCase.execute()
        }
    }
}