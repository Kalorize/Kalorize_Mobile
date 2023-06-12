package com.kalorize.kalorizeappmobile.data.remote

import androidx.compose.ui.text.font.FontWeight
import com.kalorize.kalorizeappmobile.data.remote.body.*
import com.kalorize.kalorizeappmobile.data.remote.response.*
import com.kalorize.kalorizeappmobile.data.remote.response.F2hwgResponse
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import com.kalorize.kalorizeappmobile.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiRepository(private val apiService: ApiService) {

    suspend fun login(body: LoginBody): Result<LoginResponse> {
        return apiService.login(body)
    }

    suspend fun register(body: RegisterBody): Result<RegisterResponse> {
        return apiService.register(body)
    }

    suspend fun reqOtp(body: RequestOtpBody): Result<SimpleResponse> {
        return apiService.requestOtp(body)
    }

    suspend fun validateOtp(body: ValidatingOtpBody): Result<SimpleResponse> {
        return apiService.validateOtp(body)
    }

    suspend fun rePassword(body: UpdatePassBody): Result<SimpleResponse> {
        return apiService.updatePass(body)
    }


    suspend fun getRecommendation(token: String): Result<RecommendationResponse> {
        return apiService.getRecommendation("Bearer $token")
    }

    suspend fun getRecommendationHistory(
        token: String,
        date: String
    ): Result<RecommendationHistoryResponse> {
        return apiService.getRecommendationHistory("Bearer $token", date)
    }

    suspend fun chooseFood(token: String, body: ChooseFoodBody): Result<ChooseFoodResponse> {
        return apiService.chooseFood("Bearer $token", body)
    }

    suspend fun f2hwg(token: String, file: MultipartBody.Part): Result<F2hwgResponse> {
        return apiService.f2hwg("Bearer $token", file)

    }

    suspend fun foodRec(
        token: String,
        gender: RequestBody,
        age: Float,
        height: Float,
        weight: Float,
        activity: RequestBody,
        target: RequestBody,
    ): Result<RecommendationResponse> {
        return apiService.foodRec(
            "Bearer $token",
            gender,
            age,
            weight,
            height,
            activity,
            target
        )
    }

    suspend fun getFoodDetail(token: String, id: String): Result<FoodDetailResponse> {
        return apiService.foodDetail("Bearer $token", id)
    }

    suspend fun editPhotoProfile(
        token: String,
        file: MultipartBody.Part
    ): Result<RecommendationResponse> {
        return apiService.editPhotoProfile("Bearer $token", file)
    }

    suspend fun editProfile(
        token: String,
        name: RequestBody,
        gender: RequestBody,
        age: Float,
        height: Float,
        weight: Float,
    ): Result<RecommendationResponse> {
        return apiService.editProfile("Bearer $token", name, age, gender, weight, height)
    }

    suspend fun editPassword(
        token: String,
        password:RequestBody,
    ):Result<RecommendationResponse>{
        return apiService.editPassword("Bearer $token", password)
    }
}