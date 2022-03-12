package com.tatsuki.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatsuki.data.api.citysearch.CitySearchApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class CitySearchApiTest {

    private lateinit var moshi: Moshi
    private lateinit var httpLoggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var citySearchApi: CitySearchApi

    @Before
    fun setup() {
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
            .baseUrl(BuildConfig.CITY_SEARCH_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        citySearchApi = retrofit.create(CitySearchApi::class.java)
    }

    @Test
    fun CitySearchAPIを実行してレスポンスが取得できること() {
        runBlocking {
            val result = citySearchApi.getCity("01")
            assertThat(result).isNotNull
            assertThat(result.status).isEqualTo("OK")
            assertThat(result.data.count()).isGreaterThan(0)
        }
    }
}