package com.example.modmobilesmartsale.data.model.addtocartmodel


import com.google.gson.annotations.SerializedName

data class AddtocartResponseItem(
    @SerializedName("cartCount")
    val cartCount: String,
    @SerializedName("response")
    val response: String,
    @SerializedName("sessionid")
    val sessionid: String,
    @SerializedName("status")
    val status: String
)