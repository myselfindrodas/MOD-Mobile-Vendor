package com.example.modmobilesmartsale.data.model.otpsentmodel


import com.google.gson.annotations.SerializedName

data class SentOTPResponse(
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("type")
    val type: String
)