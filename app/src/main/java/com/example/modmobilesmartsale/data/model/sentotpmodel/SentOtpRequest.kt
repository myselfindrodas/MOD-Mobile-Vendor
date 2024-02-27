package com.example.modmobilesmartsale.data.model.sentotpmodel

import com.google.gson.annotations.SerializedName

data class SentOtpRequest(
    @SerializedName("mobile_no")
    val mobile_no: String,
    @SerializedName("token")
    val token: String,
)