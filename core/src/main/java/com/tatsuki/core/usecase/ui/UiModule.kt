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
    abstract fun bindSlideShowView(
        slideShowViewImpl: SlideShowViewImpl
    ): ISlideShowView

    @Binds
    abstract fun bindCurrentWeatherView(
        currentWeatherViewImpl: CurrentWeatherViewImpl
    ): ICurrentWeatherView

    @Binds
    abstract fun bindWeatherDetailView(
        weatherDetailViewImpl: WeatherDetailViewImpl
    ): IWeatherDetailView

    @Binds
    abstract fun bindDeviceListView(
        deviceListViewImpl: DeviceListViewImpl
    ): IDeviceListView

    @Binds
    abstract fun bindSensorDataView(
        sensorDataViewImpl: SensorDataViewImpl
    ): ISensorDataView

    @Binds
    abstract fun bindsCityListView(
        cityListViewImpl: CityListViewImpl
    ): ICityListView

    @Binds
    abstract fun bindsPlaceNameView(
        placeNameViewImpl: PlaceNameViewImpl
    ): IPlaceNameView
}