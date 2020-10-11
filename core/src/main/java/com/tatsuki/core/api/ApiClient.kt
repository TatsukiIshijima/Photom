package com.tatsuki.core.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatsuki.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

open class ApiClient(
    url: String
) {

    companion object {
        private val TAG = ApiClient::class.java.simpleName
        private const val TIMEOUT_SEC = 10L
    }

    var retrofit: Retrofit

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val oktHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            oktHttpClient.addInterceptor(loggingInterceptor)
        }
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(oktHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}