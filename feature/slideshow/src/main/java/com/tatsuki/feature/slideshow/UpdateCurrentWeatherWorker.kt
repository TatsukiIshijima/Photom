package com.tatsuki.feature.slideshow

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UpdateCurrentWeatherWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        return Result.success()
    }
}