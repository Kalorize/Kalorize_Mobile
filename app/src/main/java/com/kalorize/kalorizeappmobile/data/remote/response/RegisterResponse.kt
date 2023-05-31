package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val registerData: RegisterData,

	@field:SerializedName("status")
	val status: String
)

data class RegisterData(

	@field:SerializedName("user")
	val registerUser: RegisterUser
)

data class RegisterUser(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
