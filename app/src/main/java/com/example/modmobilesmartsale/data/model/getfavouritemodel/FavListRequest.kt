package com.example.modmobilesmartsale.data.model.getfavouritemodel


import com.google.gson.annotations.SerializedName

data class FavListRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("userid")
    val userid: String
)