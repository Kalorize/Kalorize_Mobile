package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationHistoryResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val pastRecommendation: PastRecommendation? = null,
)

data class PastRecommendation(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("calories")
    val calories: Float,

    @field:SerializedName("protein")
    val protein: Float,

    @field:SerializedName("breakfast")
    val breakfast: FoodItem,

    @field:SerializedName("lunch")
    val lunch: FoodItem,

    @field:SerializedName("dinner")
    val dinner: FoodItem,
)
