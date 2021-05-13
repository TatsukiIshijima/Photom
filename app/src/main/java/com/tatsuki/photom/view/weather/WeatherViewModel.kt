package com.tatsuki.photom.view.weather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.entity.CurrentWeatherInfoItem
import com.tatsuki.core.entity.DailyWeatherEntity
import com.tatsuki.core.entity.TimelyWeatherEntity
import com.tatsuki.core.entity.WeatherCondition
import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ShowWeatherDetailUseCase
import com.tatsuki.core.usecase.ui.IWeatherDetailView
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    @ApplicationContext context: Context,
    weatherRepository: WeatherRepository,
    placeRepository: PlaceRepository
) : ViewModel(), IWeatherDetailView {

    private var job: Job? = null

    private val showWeatherDetailUseCase =
        ShowWeatherDetailUseCase(this, weatherRepository, placeRepository)

    private val _autoTransitionMutableLiveData = MutableLiveData<Unit>()
    private val _showPlaceNameMutableLiveData = MutableLiveData<String>()
    private val _showCurrentWeatherConditionMutableLiveData = MutableLiveData<WeatherCondition>()
    private val _showCurrentTempMutableLiveData = MutableLiveData<Int>()
    private val _showCurrentWeatherDetailMutableLiveData =
        MutableLiveData<List<CurrentWeatherInfoItem>>()
    private val _showTimelyWeatherMutableLiveData = MutableLiveData<List<TimelyWeatherEntity>>()
    private val _showDailyWeatherMutableLiveData = MutableLiveData<List<DailyWeatherEntity>>()

    val autoTransitionLiveData: LiveData<Unit> = _autoTransitionMutableLiveData
    val showPlaceLiveData: LiveData<String> = _showPlaceNameMutableLiveData
    val showCurrentWeatherConditionLiveData: LiveData<WeatherCondition> =
        _showCurrentWeatherConditionMutableLiveData
    val showCurrentTempLiveData: LiveData<Int> = _showCurrentTempMutableLiveData
    val showCurrentWeatherDetailLiveData: LiveData<List<CurrentWeatherInfoItem>> =
        _showCurrentWeatherDetailMutableLiveData
    val showTimelyWeatherLiveData: LiveData<List<TimelyWeatherEntity>> =
        _showTimelyWeatherMutableLiveData
    val showDailyWeatherLiveData: LiveData<List<DailyWeatherEntity>> =
        _showDailyWeatherMutableLiveData

    fun startAutoTransitionTimer() {
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                delay(10000)
                _autoTransitionMutableLiveData.value = Unit
            }
        }

    }

    fun stopAutoTransitionTimer() {
        job?.cancel()
    }

    fun showWeatherDetail() {
        viewModelScope.launch {
            showWeatherDetailUseCase.execute()
        }
    }

    override fun showPlace(name: String) {
        _showPlaceNameMutableLiveData.value = name
    }

    override fun showCurrentWeather(condition: WeatherCondition, temp: Int) {
        _showCurrentWeatherConditionMutableLiveData.value = condition
        _showCurrentTempMutableLiveData.value = temp
    }

    override fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>) {
        _showCurrentWeatherDetailMutableLiveData.value = list
    }

    override fun showTimelyWeather(list: List<TimelyWeatherEntity>) {
        _showTimelyWeatherMutableLiveData.value = list
    }

    override fun showDailyWeather(list: List<DailyWeatherEntity>) {
        _showDailyWeatherMutableLiveData.value = list
    }
}