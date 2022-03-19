package com.tatsuki.feature.slideshow

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchCurrentWeatherUseCase
import com.tatsuki.core.usecase.FetchSlideImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val fetchSlideImageUseCase: FetchSlideImageUseCase,
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
) : ViewModel() {

    //    private val workManager = WorkManager.getInstance(context)
//    private var workId: UUID? = null
    private var timber: Timer? = null
    private val timberTask: TimerTask.() -> Unit = {
        showCurrentWeather()
    }

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

//    fun startUpdateCurrentWeatherWork(): LiveData<WorkInfo> {
//        Timber.d("startUpdateCurrentWeatherWork")
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//        val updateWorkRequest =
//            PeriodicWorkRequestBuilder<UpdateCurrentWeatherWorker>(Duration.ofMinutes(15))
//                .setConstraints(constraints)
//                .addTag(UPDATE_CURRENT_WEATHER_TAG)
//                .build()
//        workManager.enqueueUniquePeriodicWork(
//            UPDATE_CURRENT_WEATHER_WORK,
//            ExistingPeriodicWorkPolicy.REPLACE,
//            updateWorkRequest
//        )
//        workId = updateWorkRequest.id
//        return workManager.getWorkInfoByIdLiveData(updateWorkRequest.id)
//    }
//
//    fun stopUpdateCurrentWeatherWork() {
//        workId?.let {
//            Timber.d("stopUpdateCurrentWeatherWork")
//            workManager.cancelWorkById(it)
//        }
//    }

    // FIXME: replace WorkManager
    fun startUpdateCurrentWeatherTimer() {
        Timber.d("startUpdateCurrentWeatherTimer")
        timber = Timer()
        timber?.schedule(0, INTERVAL, timberTask)
    }

    fun stopUpdateCurrentWeatherTimer() {
        Timber.d("stopUpdateCurrentWeatherTimer")
        if (timber != null) {
            timber?.cancel()
            timber = null
        }
    }

    companion object {
        //        private const val UPDATE_CURRENT_WEATHER_TAG = "UpdateCurrentWeatherTag"
//        private const val UPDATE_CURRENT_WEATHER_WORK = "UpdateCurrentWeatherWork"
        private const val INTERVAL = 1000L * 60 * 60
    }
}