package com.example.modmobilesmartsale.data.model.defaultaddressmodel


import com.google.gson.annotations.SerializedName

data class DefaultAddressRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userid")
    val userid: String
)