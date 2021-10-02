package com.tatsuki.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatsuki.data.api.openweather.OpenWeatherApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class OpenWeatherApiTest {

    private lateinit var moshi: Moshi
    private lateinit var httpLoggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var openWeatherApi: OpenWeatherApi

    @Before
    fun initialize() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = OkHttpClient.Builder()
            .apply {
                addInterceptor(httpLoggingInterceptor)
                connectTimeout(10L, TimeUnit.SECONDS)
                readTimeout(10L, TimeUnit.SECONDS)
            }.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    @Test
    fun OneCallAPIを実行してレスポンスが取得できること() {
        runBlocking {
            val result = openWeatherApi.getOneCall(lat = 35.6800897, lon = 139.7654783)
            assertThat(result).isNotNull
        }
    }
}