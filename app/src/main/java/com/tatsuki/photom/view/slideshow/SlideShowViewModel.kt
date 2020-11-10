package com.tatsuki.photom.view.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.entity.CurrentWeatherEntity
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class SlideShowViewModel(
    weatherRepository: WeatherRepository,
    slideImageRepository: SlideImageRepository
): ViewModel(), ICurrentWeatherView, ISlideShowView {

    private val fetchCurrentWeatherUseCase = FetchCurrentWeatherUseCase(this, weatherRepository)
    private val fetchSlideImageUseCase = FetchSlideImageUseCase(this, slideImageRepository)

    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val slideImageUrlMutableLiveData = MutableLiveData<List<StorageReference>>()

    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData
    val slideImageUrlLiveData: LiveData<List<StorageReference>> = slideImageUrlMutableLiveData

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            fetchCurrentWeatherUseCase.execute(lat = 35.68, lon = 139.77)
        }
    }

    @ExperimentalCoroutinesApi
    fun fetchSlideImage() {
        viewModelScope.launch {
            fetchSlideImageUseCase.execute(TimeZone.Morning)
        }
    }

    override fun showCurrentWeather(entity: CurrentWeatherEntity) {

    }

    override fun showSlide(refList: List<StorageReference>) {
        slideImageUrlMutableLiveData.value = refList
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