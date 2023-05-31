package com.kalorize.kalorizeappmobile.data.remote.body

import com.google.gson.annotations.SerializedName

data class UpdatePassBody(

	@field:SerializedName("newpassword")
	val newpassword: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("renewpassword")
	val renewpassword: String
)
