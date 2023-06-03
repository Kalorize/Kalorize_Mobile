package com.kalorize.kalorizeappmobile.data.remote

import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.body.RegisterBody
import com.kalorize.kalorizeappmobile.data.remote.body.UpdatePassBody
import com.kalorize.kalorizeappmobile.data.remote.body.ValidatingOtpBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import com.kalorize.kalorizeappmobile.data.remote.retrofit.ApiService

class ApiRepository(private val apiService: ApiService) {

    suspend fun login(body: LoginBody): Result<LoginResponse> {
        return apiService.login(body)
    }

    suspend fun register(body: RegisterBody): Result<RegisterResponse> {
        return apiService.register(body)
    }

    suspend fun reqOtp(email: String): Result<SimpleResponse> {
        return  apiService.requestOtp(email)
    }

    suspend fun validateOtp(body: ValidatingOtpBody): Result<SimpleResponse>{
        return apiService.validateOtp(body)
    }

    suspend fun rePassword(body: UpdatePassBody): Result<SimpleResponse>{
        return apiService.updatePass(body)
    }
}