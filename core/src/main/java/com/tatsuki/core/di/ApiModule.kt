package com.tatsuki.core.di

import com.tatsuki.core.api.OpenWeatherApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideOpenWeatherApiClient(): OpenWeatherApiClient {
        return OpenWeatherApiClient()
    }
}