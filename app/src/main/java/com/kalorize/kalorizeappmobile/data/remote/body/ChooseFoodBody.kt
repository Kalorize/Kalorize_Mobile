package com.kalorize.kalorizeappmobile.data.remote.body

import com.google.gson.annotations.SerializedName

data class ChooseFoodBody(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("lunch")
	val lunch: Lunch,

	@field:SerializedName("breakfast")
	val breakfast: Breakfast,

	@field:SerializedName("dinner")
	val dinner: Dinner
)

data class Lunch(

	@field:SerializedName("id")
	val id: Int
)

data class Breakfast(

	@field:SerializedName("id")
	val id: Int
)

data class Dinner(

	@field:SerializedName("id")
	val id: Int
)
