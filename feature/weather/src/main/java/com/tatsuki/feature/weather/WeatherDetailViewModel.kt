package com.tatsuki.feature.weather

import androidx.lifecycle.ViewModel
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.core.usecase.ui.IWeatherDetailView
import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.data.entity.WeatherCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(

) : ViewModel() {

    private val loadingView = object : ILoadingView {
        override fun showLoading() {

        }

        override fun hideLoading() {

        }
    }

    private val errorView = object : IErrorView {
        override fun showError(e: Exception) {

        }

        override fun showError(code: Int?, message: String?) {

        }

        override fun showInternalServerError() {

        }

        override fun showNetworkError() {

        }
    }

    private val weatherView = object : IWeatherDetailView {
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
}