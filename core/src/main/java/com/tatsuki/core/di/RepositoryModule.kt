package com.tatsuki.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.tatsuki.core.api.OpenWeatherApiClient
import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(client: OpenWeatherApiClient): WeatherRepository =
        WeatherRepository(client)

    @Singleton
    @Provides
    fun providePlaceRepository(db: FirebaseFirestore): PlaceRepository =
        PlaceRepository(db)
}