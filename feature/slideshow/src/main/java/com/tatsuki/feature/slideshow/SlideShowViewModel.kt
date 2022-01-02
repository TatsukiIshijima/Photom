package com.tatsuki.feature.slideshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchCurrentWeatherUseCase
import com.tatsuki.core.usecase.FetchSlideImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    private val fetchSlideImageUseCase: FetchSlideImageUseCase,
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
) : ViewModel() {

    val loadingFlow = fetchSlideImageUseCase
        .loadingView
        .state
        .mutableLoadingFlow
        .asStateFlow()

    val photoListFlow = fetchSlideImageUseCase
        .slideShowView
        .state
        .mutablePhotoListFlow
        .asStateFlow()

    val currentWeatherFlow = fetchCurrentWeatherUseCase
        .currentWeatherView
        .state
        .mutableCurrentWeatherFlow
        .asStateFlow()

    fun showSlide() {
        viewModelScope.launch {
            fetchSlideImageUseCase.execute()
        }
    }

    fun showCurrentWeather() {
        viewModelScope.launch {
            fetchCurrentWeatherUseCase.execute()
        }
    }
}