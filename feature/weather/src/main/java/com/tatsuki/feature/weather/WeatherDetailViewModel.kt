package com.tatsuki.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchWeatherDetailUseCase
import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val mutablePlaceFlow = MutableStateFlow("")
    val placeFlow = mutablePlaceFlow.asStateFlow()

    private val mutableConditionFlow = MutableStateFlow<WeatherCondition?>(null)
    val conditionFlow = mutableConditionFlow.asStateFlow()

    private val mutableTemperatureFlow = MutableStateFlow<Int?>(null)
    val temperatureFlow = mutableTemperatureFlow.asStateFlow()

    private val mutableWeatherInfoListFlow = MutableStateFlow(listOf<CurrentWeatherInfoItem>())
    val weatherInfoListFlow = mutableWeatherInfoListFlow.asStateFlow()

    private val mutableTimelyWeatherFlow = MutableStateFlow(listOf<TimelyWeatherEntity>())
    val timelyWeatherFlow = mutableTimelyWeatherFlow.asStateFlow()

    private val mutableDailyWeatherFlow = MutableStateFlow(listOf<DailyWeatherEntity>())
    val dailyWeatherFlow = mutableDailyWeatherFlow.asStateFlow()

    fun fetchWeatherDetail(locationName: String = "東京都渋谷区") {
        viewModelScope.launch {
            fetchWeatherDetailUseCase.execute(locationName)
        }
    }
}