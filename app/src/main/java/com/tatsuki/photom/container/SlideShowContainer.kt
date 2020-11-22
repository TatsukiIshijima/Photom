package com.tatsuki.photom.container

import androidx.work.WorkManager
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.photom.view.slideshow.SlideShowViewModel

class SlideShowContainer(
    private val weatherRepository: WeatherRepository,
    private val slideImageRepository: SlideImageRepository,
    private val workManager: WorkManager
) : FactoryInterface<SlideShowViewModel> {

    override fun create(): SlideShowViewModel =
        SlideShowViewModel(weatherRepository, slideImageRepository, workManager)
}