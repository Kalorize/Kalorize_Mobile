package com.kalorize.kalorizeappmobile.data.remote.body

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("repassword")
    val rePassword: String
)
