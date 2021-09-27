package com.tatsuki.core.di

import com.tatsuki.data.api.OpenWeatherApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOpenWeatherApiClient(): OpenWeatherApiClient {
        return OpenWeatherApiClient()
    }
}