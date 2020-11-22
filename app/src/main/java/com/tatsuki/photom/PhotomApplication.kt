package com.tatsuki.photom

import android.app.Application
import com.tatsuki.photom.container.PhotomContainer
import timber.log.Timber

class PhotomApplication: Application() {

    lateinit var photomContainer: PhotomContainer

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Container内にFirebase依存があり、初期化前に読んでしまうと Initialize Error となるので
        // ライフサイクルに合わせて Container を生成する
        photomContainer = PhotomContainer(applicationContext)
    }
}