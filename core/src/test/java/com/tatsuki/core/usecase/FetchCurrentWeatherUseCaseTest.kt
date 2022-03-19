package com.tatsuki.core.usecase

import com.google.firebase.firestore.GeoPoint
import com.tatsuki.core.State
import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.repository.WeatherRepository
import com.tatsuki.core.usecase.ui.ICurrentWeatherView
import com.tatsuki.data.api.openweather.response.CurrentResponse
import com.tatsuki.data.api.openweather.response.OneCallResponse
import com.tatsuki.data.api.openweather.response.PlaceResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.*

class FetchCurrentWeatherUseCaseTest {

    @Test
    fun 現在の天気情報が取得できた場合に現在の天気と温度が表示されること() {
        val weatherRepository = mock<WeatherRepository> {
            on { getWeather(lat = 38.79, lon = 137.79) } doReturn flow<State<OneCallResponse>> {
                emit(
                    State.success(
                        OneCallResponse(
                            latitude = 38.79,
                            longitude = 137.79,
                            timezone = "",
                            timezoneOffset = 0.0,
                            current = CurrentResponse(0, 0.0, 0, 0, 0.0, listOf()),
                            hourly = listOf(),
                            daily = listOf()
                        )
                    )
                )
            }
        }
        val placeRepository = mock<PlaceRepository> {
            on { fetchPlace() } doReturn flow<State<PlaceResponse?>> {
                emit(State.success(PlaceResponse(name = "東京", location = GeoPoint(38.79, 137.79))))
            }
        }
        val view = mock<ICurrentWeatherView> {
            on { showLoading() } doAnswer { }
            on { hideLoading() } doAnswer { }
            on { showCurrentWeather(any()) } doAnswer { }
        }

        val usecase = FetchCurrentWeatherUseCase(weatherRepository, placeRepository)
        runBlocking {
            usecase.execute(view)
        }

        verify(view, times(1)).showLoading()
        verify(placeRepository, times(1)).fetchPlace()
        verify(weatherRepository, times(1)).getWeather(lat = 38.79, lon = 137.79)
        verify(view, times(1)).showCurrentWeather(any())
        verify(view, never()).showError(any())
        verify(view, times(1)).hideLoading()
    }
}