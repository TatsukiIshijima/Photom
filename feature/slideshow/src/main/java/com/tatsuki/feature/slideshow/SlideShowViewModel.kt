package com.tatsuki.feature.slideshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchCurrentWeatherUseCase
import com.tatsuki.core.usecase.FetchSlideImageUseCase
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.core.usecase.ui.ISlideShowView
import com.tatsuki.data.entity.CurrentWeatherEntity
import com.tatsuki.data.entity.PhotoEntity
import com.tatsuki.data.entity.PhotoListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    private val fetchSlideImageUseCase: FetchSlideImageUseCase,
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
) : ViewModel() {

    private val mutableLoadingFlow = MutableStateFlow(false)
    val loadingFlow = mutableLoadingFlow.asStateFlow()

    private val mutablePhotoListFlow = MutableStateFlow(listOf<PhotoEntity>())
    val photoListFlow = mutablePhotoListFlow.asStateFlow()

    private val mutableCurrentWeatherFlow = MutableStateFlow<CurrentWeatherEntity?>(null)
    val currentWeatherFlow = mutableCurrentWeatherFlow

    private val loadingView = object : ILoadingView {
        override fun showLoading() {
            mutableLoadingFlow.value = true
        }

        override fun hideLoading() {
            mutableLoadingFlow.value = false
        }
    }

    private val errorView = object : IErrorView {
        override fun showError(e: Exception) {}

        override fun showError(code: Int?, message: String?) {}

        override fun showInternalServerError() {}

        override fun showNetworkError() {}
    }

    private val slideShowView = object : ISlideShowView {
        override fun showSlide(photoListEntity: PhotoListEntity) {
            mutablePhotoListFlow.value = photoListEntity.list
        }
    }

    private val weatherView = object : ICurrentWeatherView {
        override fun showCurrentWeather(entity: CurrentWeatherEntity) {
            mutableCurrentWeatherFlow.value = entity
        }
    }

    fun showSlide() {
        viewModelScope.launch {
            fetchSlideImageUseCase.execute(
                errorView,
                loadingView,
                slideShowView
            )
        }
    }

    fun showCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            fetchCurrentWeatherUseCase.execute(
                weatherView,
                lat,
                lon
            )
        }
    }
}