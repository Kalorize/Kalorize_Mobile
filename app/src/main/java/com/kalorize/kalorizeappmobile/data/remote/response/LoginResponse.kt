package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData,

	@field:SerializedName("status")
	val status: String
)

data class LoginUser(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)

data class LoginData(

	@field:SerializedName("user")
	val user: LoginUser,

	@field:SerializedName("token")
	val token: String
)
