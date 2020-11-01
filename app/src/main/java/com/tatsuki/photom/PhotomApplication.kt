package com.tatsuki.photom

import android.app.Application
import com.tatsuki.photom.container.PhotomContainer

class PhotomApplication: Application() {
    val photomContainer = PhotomContainer()
}