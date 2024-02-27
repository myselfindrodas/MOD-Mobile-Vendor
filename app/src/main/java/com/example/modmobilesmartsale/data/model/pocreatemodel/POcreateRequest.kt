package com.example.modmobilesmartsale.data.model.pocreatemodel


import com.google.gson.annotations.SerializedName

data class POcreateRequest(
    @SerializedName("POJSON")
    val pOJSON: POJSON,
    @SerializedName("token")
    val token: String
)