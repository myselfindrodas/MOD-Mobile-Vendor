package com.example.modmobilesmartsale.data.model.otpvalidatemodel


import com.google.gson.annotations.SerializedName

data class OTPverifiedResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String
)