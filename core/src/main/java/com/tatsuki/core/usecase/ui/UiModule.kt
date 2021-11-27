package com.tatsuki.core.usecase.ui

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UiModule {

    @Binds
    abstract fun bindLoadingView(
        loadingViewImpl: LoadingViewImpl
    ): ILoadingView

    @Binds
    abstract fun bindErrorView(
        errorViewImpl: ErrorViewImpl
    ): IErrorView

    @Binds
    abstract fun bindWeatherDetailView(
        weatherDetailViewImpl: WeatherDetailViewImpl
    ): IWeatherDetailView
}