package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChooseFoodResponse(

	@field:SerializedName("data")
	val data: FoodData,

	@field:SerializedName("status")
	val status: String
)

data class FoodData(

	@field:SerializedName("updateFood")
	val updateFood: UpdateFood
)

data class UpdateFood(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("lunchId")
	val lunchId: Int,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("dinnerId")
	val dinnerId: Int,

	@field:SerializedName("calories")
	val calories: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("breakfastId")
	val breakfastId: Int
)
