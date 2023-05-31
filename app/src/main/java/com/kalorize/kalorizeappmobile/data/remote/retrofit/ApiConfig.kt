package com.kalorize.kalorizeappmobile.data.remote.retrofit

import android.content.Context

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.kalorize.kalorizeappmobile.BuildConfig
import com.kalorize.kalorizeappmobile.exception.ResultCallAdapterFactory
import retrofit2.CallAdapter

object ApiConfig {

    private const val BASE_URL = "https://kalorize-be-cwx4yokorq-et.a.run.app/"

    fun getApiService(context: Context): ApiService {

        val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}