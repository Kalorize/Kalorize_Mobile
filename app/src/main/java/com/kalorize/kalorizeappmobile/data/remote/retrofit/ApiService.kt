package com.kalorize.kalorizeappmobile.data.remote.retrofit

import com.kalorize.kalorizeappmobile.data.remote.body.*
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/auth/login")
    suspend fun login(
        @Body body: LoginBody
    ): Result<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/auth/register")
    suspend fun register(
        @Body body: RegisterBody
    ): Result<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/auth/forgot-password")
    suspend fun requestOtp(
        @Body body: RequestOtpBody
    ): Result<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/auth/forgot-password")
    suspend fun validateOtp(
        @Body body: ValidatingOtpBody
    ): Result<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/auth/forgot-password")
    suspend fun updatePass(
        @Body body: UpdatePassBody
    ): Result<SimpleResponse>

}