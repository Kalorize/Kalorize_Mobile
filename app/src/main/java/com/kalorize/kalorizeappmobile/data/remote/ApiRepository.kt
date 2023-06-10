package com.kalorize.kalorizeappmobile.data.remote

import com.kalorize.kalorizeappmobile.data.remote.body.*
import com.kalorize.kalorizeappmobile.data.remote.response.*
import com.kalorize.kalorizeappmobile.data.remote.retrofit.ApiService

class ApiRepository(private val apiService: ApiService) {

    suspend fun login(body: LoginBody): Result<LoginResponse> {
        return apiService.login(body)
    }

    suspend fun register(body: RegisterBody): Result<RegisterResponse> {
        return apiService.register(body)
    }

    suspend fun reqOtp(body: RequestOtpBody): Result<SimpleResponse> {
        return  apiService.requestOtp(body)
    }

    suspend fun validateOtp(body: ValidatingOtpBody): Result<SimpleResponse>{
        return apiService.validateOtp(body)
    }

    suspend fun rePassword(body: UpdatePassBody): Result<SimpleResponse>{
        return apiService.updatePass(body)
    }

    suspend fun getRecommendation(token: String): Result<RecommendationResponse>{
        return apiService.getRecommendation("Bearer $token")
    }

    suspend fun getRecommendationHistory(token: String, date: String): Result<RecommendationHistoryResponse> {
        return apiService.getRecommendationHistory("Bearer $token" , date)
    }

    suspend fun chooseFood(token: String, body: ChooseFoodBody): Result<ChooseFoodResponse>{
        return apiService.chooseFood("Bearer $token" ,body)
    }

    suspend fun getFoodDetail(token: String, id: String): Result<FoodDetailResponse>{
        return apiService.foodDetail("Bearer $token" ,id)
    }
}