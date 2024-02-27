package com.example.modmobilesmartsale.data.model.viewaddressmodel


import com.google.gson.annotations.SerializedName

data class ViewAddressRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("user_id")
    val userId: String
)