package com.example.modmobilesmartsale.data.model.dashboardmodel


import com.google.gson.annotations.SerializedName

data class LatestEditionImg(
    @SerializedName("banneroffre")
    val banneroffre: String,
    @SerializedName("price_range")
    val price_range: String
)