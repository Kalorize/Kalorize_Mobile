package com.kalorize.kalorizeappmobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class F2hwgResponse(
    @SerializedName("gender")
    val gender: String,

    @SerializedName("height")
    val height: Float,

    @SerializedName("weight")
    val weight: Float
)