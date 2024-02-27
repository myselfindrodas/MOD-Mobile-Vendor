package com.example.modmobilesmartsale.data.model.viewaddressmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("addressdata")
    val addressdata: List<Addressdata>,
    @SerializedName("UserId")
    val userId: String
)