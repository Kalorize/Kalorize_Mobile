package com.kalorize.kalorizeappmobile.data.remote

import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.body.RegisterBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import com.kalorize.kalorizeappmobile.data.remote.retrofit.ApiService

class ApiRepository(private val apiService: ApiService) {

    suspend fun login(body: LoginBody): Result<LoginResponse> {
        return apiService.login(body)
    }

    suspend fun register(body: RegisterBody): RegisterResponse {
        return apiService.register(body)
    }
}