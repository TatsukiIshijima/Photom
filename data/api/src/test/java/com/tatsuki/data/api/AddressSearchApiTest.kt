package com.tatsuki.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatsuki.data.api.addresssearch.AddressSearchApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class AddressSearchApiTest {

    private lateinit var moshi: Moshi
    private lateinit var httpLoggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var addressSearchApi: AddressSearchApi

    @Before
    fun setUp() {
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
            .baseUrl(BuildConfig.ADDRESS_SEARCH_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        addressSearchApi = retrofit.create(AddressSearchApi::class.java)
    }

    @Test
    fun AddressSearchAPIを実行してレスポンスが取得できること() {
        runBlocking {
            val result = addressSearchApi.getAddress("東京都渋谷区")
            assertThat(result).isNotNull
            assertThat(result.count()).isEqualTo(1)
            assertThat(result.first().geometry.coordinates.count()).isEqualTo(2)
        }
    }
}