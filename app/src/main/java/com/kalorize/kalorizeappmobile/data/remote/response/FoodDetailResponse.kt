package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodDetailResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val foodData: Food? = null,
)

data class Food(
    @field:SerializedName("food")
    val foodItem: FoodItem
)
