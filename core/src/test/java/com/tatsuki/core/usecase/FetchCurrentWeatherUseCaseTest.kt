package com.tatsuki.core.usecase

import com.nhaarman.mockitokotlin2.*
import com.tatsuki.core.api.OpenWeatherApiClient
import com.tatsuki.core.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCurrentWeatherUseCaseTest {

    private lateinit var client: OpenWeatherApiClient
    private lateinit var repository: WeatherRepository
    private lateinit var view: ICurrentWeatherView
    private lateinit var usecase: FetchCurrentWeatherUseCase

    @Before
    fun initialize() {
        client = OpenWeatherApiClient()
        repository = WeatherRepository(client)
        view = mock()
        usecase = FetchCurrentWeatherUseCase(view, repository)
    }

    @Test
    fun 現在の天気情報が取得できた場合に現在の天気と温度が表示されること() {
        runBlocking {
            usecase.execute(lat = 35.68, lon = 139.77)

            verify(view, times(1)).showLoading()
            verify(view).showCurrentWeather(any())
            verify(view, never()).showError(any())
            verify(view, times(1)).hideLoading()
        }
    }
}