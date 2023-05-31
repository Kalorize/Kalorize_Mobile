package com.kalorize.kalorizeappmobile.data.remote.body

import com.google.gson.annotations.SerializedName

data class ValidatingOtpBody(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("otp")
    val otp: String
)
