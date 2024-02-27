package com.example.modmobilesmartsale.data.model.tokenmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("expire")
    val expire: Int,
    @SerializedName("token")
    val token: String
)