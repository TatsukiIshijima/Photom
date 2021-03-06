package com.tatsuki.core.repository

import com.tatsuki.core.State
import com.tatsuki.core.api.OpenWeatherApiClient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    private lateinit var client: OpenWeatherApiClient
    private lateinit var repository: WeatherRepository

    @Before
    fun initialize() {
        client = OpenWeatherApiClient()
        repository = WeatherRepository(client)
    }

    @Test
    fun OneCallAPIの実行しデータ取得できること() {
        runBlocking {
            repository.getWeather(lat = 35.68, lon = 139.77)
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