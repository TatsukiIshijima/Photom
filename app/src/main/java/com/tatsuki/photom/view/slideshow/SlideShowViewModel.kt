package com.tatsuki.photom.view.slideshow

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.entity.CurrentWeatherEntity
import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.FetchCurrentWeatherUseCase
import com.tatsuki.core.usecase.FetchSlideImageUseCase
import com.tatsuki.core.usecase.TimeZone
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.core.usecase.ui.ISlideShowView
import com.tatsuki.photom.UpdateWeatherWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    @ApplicationContext context: Context,
    weatherRepository: WeatherRepository,
    slideImageRepository: SlideImageRepository,
    placeRepository: PlaceRepository
): ViewModel(), ICurrentWeatherView, ISlideShowView {

    companion object {
        const val UPDATE_WEATHER_WORK = "UPDATE_WEATHER_WORK"
        const val UPDATE_WEATHER_TAG = "UPDATE_WEATHER_TAG"
    }

    private val fetchCurrentWeatherUseCase =
        FetchCurrentWeatherUseCase(this, weatherRepository, placeRepository)
    private val fetchSlideImageUseCase = FetchSlideImageUseCase(this, slideImageRepository)
    private val workManager = WorkManager.getInstance(context)

    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val slideImageUrlMutableLiveData = MutableLiveData<List<StorageReference>>()
    private val currentWeatherIconUrlMutableLiveData = MutableLiveData<String>()
    private val currentTemperatureMutableLiveData = MutableLiveData<String>()

    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData
    val slideImageUrlLiveData: LiveData<List<StorageReference>> = slideImageUrlMutableLiveData
    val currentWeatherIconUrlLiveData: LiveData<String> = currentWeatherIconUrlMutableLiveData
    val currentTemperatureLiveData: LiveData<String> = currentTemperatureMutableLiveData

    @FlowPreview
    fun fetchCurrentWeather() {
        viewModelScope.launch {
            fetchCurrentWeatherUseCase.execute()
        }
        Timber.d("fetchCurrentWeather")

    }

    // https://speakerdeck.com/nshiba/recommendation-of-workmanager?slide=15
    // https://medium.com/androiddevelopers/workmanager-periodicity-ff35185ff006
    fun executeUpdateWeatherWork(): LiveData<WorkInfo> {
        Timber.d( "executeUpdateWeatherWork called.")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val updateWorkRequest =
            PeriodicWorkRequestBuilder<UpdateWeatherWorker>(30, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(UPDATE_WEATHER_TAG)
                .build()
        workManager.enqueueUniquePeriodicWork(
            UPDATE_WEATHER_WORK,
            ExistingPeriodicWorkPolicy.REPLACE,
            updateWorkRequest)

        return workManager.getWorkInfoByIdLiveData(updateWorkRequest.id)
    }

    fun cancelUpdateWeatherWork() {
        workManager.cancelAllWorkByTag(UPDATE_WEATHER_TAG)
    }


    fun fetchSlideImage() {
        viewModelScope.launch {
            fetchSlideImageUseCase.execute(TimeZone.Morning)
        }
        Timber.d("fetchSlideImage")
    }

    override fun showCurrentWeather(entity: CurrentWeatherEntity) {
        currentWeatherIconUrlMutableLiveData.value = "http://openweathermap.org/img/wn/${entity.icon}@2x.png"
        currentTemperatureMutableLiveData.value = "${entity.temp}"
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