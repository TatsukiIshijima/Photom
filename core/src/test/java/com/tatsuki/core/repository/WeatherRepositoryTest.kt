package com.tatsuki.core.repository

import com.tatsuki.core.State
import com.tatsuki.data.api.openweather.OpenWeatherApi
import com.tatsuki.data.api.openweather.response.CurrentResponse
import com.tatsuki.data.api.openweather.response.OneCallResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock

class WeatherRepositoryTest {

    private lateinit var repository: WeatherRepository

    @Test
    fun OneCallAPIの実行しデータ取得できること() {
        val client = mock<OpenWeatherApi> {
            onBlocking {
                getOneCall(33.44, -94.04)
            }.thenReturn(
                OneCallResponse(
                    33.44,
                    -94.04,
                    "America/Chicag",
                    -21600.0,
                    CurrentResponse(
                        1618317040,
                        284.07,
                        1019,
                        62,
                        6.0,
                        listOf()
                    ),
                    listOf(),
                    listOf()
                )
            )
        }
        repository = WeatherRepository(client)

        runBlocking {
            repository.getWeather(lat = 33.44, lon = -94.04)
                .collect { state ->
                    when (state) {
                        is State.Success -> {
                            assertThat(state.data).isNotNull
                        }
                        is State.Failed -> {
                            assertThat(state.exception).isNull()
                        }
                    }
                }
        }
    }
}