package com.bugull.android.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 5000L

fun createOKHttpClientBuilder(logging: Boolean): OkHttpClient.Builder =
    OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = if (logging) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
    }

fun createOkHttpClient(): OkHttpClient = createOKHttpClientBuilder(true)
    .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
    .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
    .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
    .build()

fun createRetrofit(client: OkHttpClient, url: String): Retrofit = Retrofit.Builder()
    .client(client)
    .baseUrl(url)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

inline fun <reified T> Retrofit.createService(): T = create(T::class.java)




