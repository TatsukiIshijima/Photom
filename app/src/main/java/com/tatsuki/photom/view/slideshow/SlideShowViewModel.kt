package com.tatsuki.photom.view.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.entity.CurrentWeatherEntity
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.FetchCurrentWeatherUseCase
import com.tatsuki.core.usecase.FetchSlideImageUseCase
import com.tatsuki.core.usecase.ICurrentWeatherView
import com.tatsuki.core.usecase.ISlideShowView

class SlideShowViewModel(
    weatherRepository: WeatherRepository,
    slideImageRepository: SlideImageRepository
): ViewModel(), ICurrentWeatherView, ISlideShowView {

    private val fetchCurrentWeatherUseCase = FetchCurrentWeatherUseCase(this, weatherRepository)
    private val fetchSlideImageUseCase = FetchSlideImageUseCase(this, slideImageRepository)

    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val slideImageUrlMutableLiveData = MutableLiveData<List<String>>()

    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData

    override fun showCurrentWeather(entity: CurrentWeatherEntity) {

    }

    override fun showSlide(refList: List<StorageReference>) {

    }

    override fun showLoading() {
        loadingMutableLiveData.value = true
    }

    override fun hideLoading() {
        loadingMutableLiveData.value = false
    }

    override fun showError(e: Exception) {

    }
}