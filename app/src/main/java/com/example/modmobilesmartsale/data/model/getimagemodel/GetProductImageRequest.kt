package com.example.modmobilesmartsale.data.model.getimagemodel


import com.google.gson.annotations.SerializedName

data class GetProductImageRequest(
    @SerializedName("imei")
    val imei: String,
    @SerializedName("token")
    val token: String
)