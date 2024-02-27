package com.example.modmobilesmartsale.data.model.otpvalidationmodel

import com.google.gson.annotations.SerializedName

data class OtpValidationRequest(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("token")
    val token: String
)