package com.tatsuki.photom

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PhotomApplication: Application()/*, Configuration.Provider*/ {

//    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

//    override fun getWorkManagerConfiguration(): Configuration =
//        Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
}