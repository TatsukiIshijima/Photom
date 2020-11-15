package com.tatsuki.photom.view.slideshow

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.entity.CurrentWeatherEntity
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class SlideShowViewModel(
    weatherRepository: WeatherRepository,
    slideImageRepository: SlideImageRepository,
    private val workManager: WorkManager
): ViewModel(), ICurrentWeatherView, ISlideShowView {

    companion object {
        private const val UPDATE_WEATHER_TAG = "UPDATE_WEATHER_TAG"
    }

    private val fetchCurrentWeatherUseCase = FetchCurrentWeatherUseCase(this, weatherRepository)
    private val fetchSlideImageUseCase = FetchSlideImageUseCase(this, slideImageRepository)

    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val slideImageUrlMutableLiveData = MutableLiveData<List<StorageReference>>()
    private val currentWeatherIconUrlMutableLiveData = MutableLiveData<String>()
    private val currentTemperatureMutableLiveData = MutableLiveData<String>()

    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData
    val slideImageUrlLiveData: LiveData<List<StorageReference>> = slideImageUrlMutableLiveData
    val currentWeatherIconUrlLiveData: LiveData<String> = currentWeatherIconUrlMutableLiveData
    val currentTemperatureLiveData: LiveData<String> = currentTemperatureMutableLiveData

    private fun fetchCurrentWeather() {
        viewModelScope.launch {
            fetchCurrentWeatherUseCase.execute(lat = 35.68, lon = 139.77)
        }
    }

    fun executeUpdateWeatherWork() {
        Log.d("SlideShowViewModel", "executeUpdateWeatherWork")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val updateWorkRequest =
            PeriodicWorkRequestBuilder<UpdateWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(UPDATE_WEATHER_TAG)
                .build()
        workManager.enqueue(updateWorkRequest)
    }

    fun cancelUpdateWeatherWork() {
        workManager.cancelAllWorkByTag(UPDATE_WEATHER_TAG)
    }

    @ExperimentalCoroutinesApi
    fun fetchSlideImage() {
        viewModelScope.launch {
            fetchSlideImageUseCase.execute(TimeZone.Morning)
        }
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

// innerだとダメらしい
class UpdateWorker(context: Context, workParams: WorkerParameters)
    : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // TODO:
                Result.success()
            } catch (error: Throwable) {
                Result.failure()
            }
        }
    }
}