package com.kalorize.kalorizeappmobile.data.remote.response

import androidx.compose.ui.text.font.FontWeight
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("status")
	val status: String
)

data class LoginData(

	@field:SerializedName("user")
	val user: LoginUser,

	@field:SerializedName("token")
	val token: String
)
data class LoginUser(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("weight")
	val weight: Float? = null,

	@field:SerializedName("age")
	val age: Float? = null,

	@field:SerializedName("height")
	val height: Float? = null,

	@field:SerializedName("activity")
	val activity: String? = null,

	@field:SerializedName("target")
	val target: String? = null,

)
