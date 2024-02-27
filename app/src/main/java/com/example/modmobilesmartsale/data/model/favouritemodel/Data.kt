package com.example.modmobilesmartsale.data.model.favouritemodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("response")
    val response: String,
    @SerializedName("type")
    val type: String
)