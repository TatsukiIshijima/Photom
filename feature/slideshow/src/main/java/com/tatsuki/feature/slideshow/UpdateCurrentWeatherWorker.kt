package com.tatsuki.feature.slideshow

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tatsuki.core.usecase.FetchCurrentWeatherUseCase
import javax.inject.Inject

class UpdateCurrentWeatherWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase

    override suspend fun doWork(): Result {
        fetchCurrentWeatherUseCase.execute()
        return Result.success()
    }
}