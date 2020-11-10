package com.tatsuki.photom.container

import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.photom.view.slideshow.SlideShowViewModel

class SlideShowContainer(
    private val weatherRepository: WeatherRepository,
    private val slideImageRepository: SlideImageRepository
) : FactoryInterface<SlideShowViewModel> {

    override fun create(): SlideShowViewModel =
        SlideShowViewModel(weatherRepository, slideImageRepository)
}