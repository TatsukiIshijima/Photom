package com.tatsuki.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatsuki.data.api.switchbot.SwitchBotApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class SwitchBotApiTest {

    private lateinit var moshi: Moshi
    private lateinit var httpLoggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var switchBotApi: SwitchBotApi

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
            .baseUrl(BuildConfig.SWITCH_BOT_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        switchBotApi = retrofit.create(SwitchBotApi::class.java)
    }

    @Test
    fun DevicesAPIを実行してレスポンスが取得できること() {
        runBlocking {
            val result = switchBotApi.getDeviceList()
            assertThat(result).isNotNull
            assertThat(result.statusCode).isEqualTo(100)
            assertThat(result.message).isEqualTo("success")
        }
    }

    // 以下は実際に機器が動作するのでコメントアウト
    // DeviceId の部分は使用したい時に書き換える
//    @Test
//    fun DeviceCommandAPIを実行してレスポンスが取得できること() {
//        runBlocking {
//            val turnOnResult = switchBotApi.postDeviceCommand(
//                "ここにDeviceId",
//                DeviceCommandRequest(
//                    "turnOn",
//                    "default",
//                    ""
//                )
//            )
//            assertThat(turnOnResult).isNotNull
//            assertThat(turnOnResult.statusCode).isEqualTo(100)
//            assertThat(turnOnResult.message).isEqualTo("success")
//
//            Thread.sleep(1000L)
//
//            val turnOffResult = switchBotApi.postDeviceCommand(
//                "ここにDeviceId",
//                DeviceCommandRequest(
//                    "turnOff",
//                    "default",
//                "command"
//                )
//            )
//            assertThat(turnOffResult).isNotNull
//            assertThat(turnOffResult.statusCode).isEqualTo(100)
//            assertThat(turnOffResult.message).isEqualTo("success")
//        }
//    }
}