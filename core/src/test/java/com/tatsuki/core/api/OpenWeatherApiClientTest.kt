package com.tatsuki.core.api

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class OpenWeatherApiClientTest {

    private lateinit var client: OpenWeatherApiClient

    @Before
    fun initialize() {
        client = OpenWeatherApiClient()
    }

    @Test
    fun OneCallAPIを実行してレスポンスが得られること() {
        runBlocking {
            val response = client.getOneCall(lat = 35.6800897, lon = 139.7654783)
            assertThat(response).isNotNull
        }
    }
}