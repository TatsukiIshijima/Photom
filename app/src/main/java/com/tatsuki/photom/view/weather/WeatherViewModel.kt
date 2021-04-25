package com.tatsuki.photom.view.weather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.entity.CurrentWeatherInfoItem
import com.tatsuki.core.entity.DailyWeatherEntity
import com.tatsuki.core.entity.TimelyWeatherEntity
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ShowWeatherDetailUseCase
import com.tatsuki.core.usecase.ui.IWeatherDetailView
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    @ApplicationContext context: Context,
    weatherRepository: WeatherRepository
) : ViewModel(), IWeatherDetailView {

    private var job: Job? = null

    private val showWeatherDetailUseCase = ShowWeatherDetailUseCase(this, weatherRepository)

    private val _autoTransitionMutableLiveData = MutableLiveData<Unit>()
    private val _showCurrentTempMutableLiveData = MutableLiveData<Int>()
    private val _showTimelyWeatherMutableLiveData = MutableLiveData<List<TimelyWeatherEntity>>()
    private val _showDailyWeatherMutableLiveData = MutableLiveData<List<DailyWeatherEntity>>()

    val autoTransitionLiveData: LiveData<Unit> = _autoTransitionMutableLiveData
    val showCurrentTempLiveData: LiveData<Int> = _showCurrentTempMutableLiveData
    val showTimelyWeatherLiveData: LiveData<List<TimelyWeatherEntity>> =
        _showTimelyWeatherMutableLiveData
    val showDailyWeatherLiveData: LiveData<List<DailyWeatherEntity>> =
        _showDailyWeatherMutableLiveData

    fun startAutoTransitionTimer() {
        job = viewModelScope.launch {
            withContext(Dispatchers.Main) {

                delay(10000)

                if (!isActive) {
                    return@withContext
                }

                Timber.d("observeTouchEvent!!")

                _autoTransitionMutableLiveData.value = Unit
            }
        }

    }

    fun stopAutoTransitionTimer() {
        job?.cancel()
    }

    fun showWeatherDetail() {
        showWeatherDetailUseCase.execute()
    }

    override fun showCurrentWeather(id: Int, temp: Int) {
        _showCurrentTempMutableLiveData.value = temp
    }

    override fun showCurrentWeatherDetail(list: List<CurrentWeatherInfoItem>) {

    }

    override fun showTimelyWeather(list: List<TimelyWeatherEntity>) {
        _showTimelyWeatherMutableLiveData.value = list
    }

    override fun showDailyWeather(list: List<DailyWeatherEntity>) {
        _showDailyWeatherMutableLiveData.value = list
    }
}