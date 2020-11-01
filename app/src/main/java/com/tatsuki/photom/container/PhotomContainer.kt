package com.tatsuki.photom.container

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tatsuki.core.api.OpenWeatherApiClient
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.repository.WeatherRepository

class PhotomContainer {

    private val firebaseStorage = Firebase.storage

    private val openWeatherApiClient = OpenWeatherApiClient()

    val weatherRepository = WeatherRepository(openWeatherApiClient)
    val slideImageRepository = SlideImageRepository(firebaseStorage)
}