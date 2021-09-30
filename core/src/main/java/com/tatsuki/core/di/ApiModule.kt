package com.tatsuki.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatsuki.core.BuildConfig
import com.tatsuki.data.api.openweather.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    // https://developer.android.com/training/dependency-injection/hilt-android#inject-provides
    // 引数部分で依存関係を Hilt に知らせる
    // 返り値で提供される型のインスタンスを Hilt に知らせる
    // 本体の処理でインスタンスの提供方法を定義
    // 提供する必要がある場合に Hilt 側で処理を実行してくれる

    @Singleton
    @Provides
    fun provideOpenWeatherApi(
        @OpenWeather retrofit: Retrofit
    ): OpenWeatherApi =
        retrofit.create(OpenWeatherApi::class.java)

    // https://developer.android.com/training/dependency-injection/hilt-android#multiple-bindings
    // baseUrl が異なる Retrofit のインスタンスを注入するため同じ Retrofit 型でも
    // 別のインスタンスを注入できるようにする

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OpenWeather

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Photom

    @Singleton
    @OpenWeather
    @Provides
    fun provideOpenWeatherRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Photom
    @Provides
    fun providePhotomRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor?
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (httpLoggingInterceptor != null) {
                    addInterceptor(httpLoggingInterceptor)
                }
                connectTimeout(10L, TimeUnit.SECONDS)
                readTimeout(10L, TimeUnit.SECONDS)
            }.build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        if (!BuildConfig.DEBUG) {
            return null
        }
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
}