package com.kalorize.kalorizeappmobile.data.remote.body

import com.google.gson.annotations.SerializedName

data class RequestOtpBody(
    @field:SerializedName("email")
    val email: String
)
