package com.kalorize.kalorizeappmobile.di

import android.content.Context
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context) : ApiRepository {
        val apiService = ApiConfig.getApiService(context)
        return ApiRepository(apiService)
    }
}