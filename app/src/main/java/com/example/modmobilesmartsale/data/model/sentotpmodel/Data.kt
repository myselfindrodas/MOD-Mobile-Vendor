package com.example.modmobilesmartsale.data.model.sentotpmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("success")
    val success: String,
    @SerializedName("userid")
    val userid: String
)