package com.kalorize.kalorizeappmobile.data.remote.retrofit

import com.kalorize.kalorizeappmobile.data.remote.body.*

import com.kalorize.kalorizeappmobile.data.remote.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

import com.kalorize.kalorizeappmobile.data.remote.response.F2hwgResponse
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    @Headers("Content-Type: application/json")
    @GET("v1/auth/me")
    suspend fun getRecommendation(
        @Header("Authorization") token: String
    ): Result<RecommendationResponse>

    @Headers("Content-Type: application/json")
    @GET("v1/user/get-food")
    suspend fun getRecommendationHistory(
        @Header("Authorization") token: String,
        @Query("date") date: String,
    ): Result<RecommendationHistoryResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/user/choose-food")
    suspend fun chooseFood(
        @Header("Authorization") token: String,
        @Body body: ChooseFoodBody
    ): Result<ChooseFoodResponse>

<<<<<<< HEAD
    @Headers("Content-Type: application/json")
    @GET("v1/food/{id}")
    suspend fun foodDetail(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Result<FoodDetailResponse>
=======
    @Multipart
    @POST("v1/f2hwg")
    suspend fun f2hwg(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Result<F2hwgResponse>

    @Multipart
    @PUT("v1/user")
    suspend fun foodRec(
        @Header("Authorization") token: String,
        @Part("gender") gender: RequestBody,
        @Part("age") age: Float,
        @Part("weight") weight: Float,
        @Part("height") height: Float,
        @Part("activity") activity: RequestBody,
        @Part("target") target: RequestBody,
    ): Result<RecommendationResponse>
>>>>>>> 0bfe94d9ca77911158da4dc09e117028ea87f669
}