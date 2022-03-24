package com.mahmoudalim.data.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("info")
    val info: String,
    @SerializedName("type")
    val type: String
) : Serializable