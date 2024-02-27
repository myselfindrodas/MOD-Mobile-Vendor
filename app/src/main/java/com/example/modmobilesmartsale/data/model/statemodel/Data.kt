package com.example.modmobilesmartsale.data.model.statemodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("code")
    val code: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("sno")
    val sno: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("state_code")
    val stateCode: String
)