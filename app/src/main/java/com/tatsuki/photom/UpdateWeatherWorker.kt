package com.tatsuki.photom

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

// innerだとダメらしいので以下参考にカスタム使用としたが、Application Class で FetchCurrentWeatherUseCase
// を作成しなくていけないことになるので困難。なので、進捗状態に応じて実行させる
// https://medium.com/androiddevelopers/customizing-workmanager-fundamentals-fdaa17c46dd2

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workParam: WorkerParameters
): Worker(context, workParam) {
    override fun doWork(): Result {
        return Result.success()
    }
}