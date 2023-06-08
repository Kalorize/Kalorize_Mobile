package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String
)

data class Reccomendation(

	@field:SerializedName("lunch")
	val lunch: List<FoodItem>,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("calories")
	val calories: Any,

	@field:SerializedName("breakfast")
	val breakfast: List<FoodItem>,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("dinner")
	val dinner: List<FoodItem>
)

data class FoodItem(

	@field:SerializedName("instructions")
	val instructions: String,

	@field:SerializedName("fiber")
	val fiber: Any,

	@field:SerializedName("totalTime")
	val totalTime: String,

	@field:SerializedName("cookTime")
	val cookTime: String,

	@field:SerializedName("calories")
	val calories: Any,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Any,

	@field:SerializedName("prepTime")
	val prepTime: String,

	@field:SerializedName("saturatedFat")
	val saturatedFat: Any,

	@field:SerializedName("sodium")
	val sodium: Any,

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("fat")
	val fat: Any,

	@field:SerializedName("ingredients")
	val ingredients: String,

	@field:SerializedName("cholesterol")
	val cholesterol: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("sugar")
	val sugar: Any
)

data class User(

	@field:SerializedName("reccomendation")
	val reccomendation: Reccomendation,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("activity")
	val activity: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("foodHistories")
	val foodHistories: List<Any>,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("target")
	val target: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("height")
	val height: Int
)

data class Data(

	@field:SerializedName("user")
	val user: User
)
