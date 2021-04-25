package com.tatsuki.core.di

import com.tatsuki.core.api.OpenWeatherApiClient
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
    fun provideWeatherRepository(client: OpenWeatherApiClient): WeatherRepository {
        return WeatherRepository(client)
    }
}