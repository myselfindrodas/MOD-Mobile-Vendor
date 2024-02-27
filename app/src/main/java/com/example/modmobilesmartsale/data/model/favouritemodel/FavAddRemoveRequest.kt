package com.example.modmobilesmartsale.data.model.favouritemodel


import com.google.gson.annotations.SerializedName

data class FavAddRemoveRequest(
    @SerializedName("productid")
    val productid: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userid")
    val userid: String
)