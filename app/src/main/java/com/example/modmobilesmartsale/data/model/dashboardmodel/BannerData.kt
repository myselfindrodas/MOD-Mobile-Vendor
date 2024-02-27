package com.example.modmobilesmartsale.data.model.dashboardmodel


import com.google.gson.annotations.SerializedName

data class BannerData(
    @SerializedName("bannerUrl")
    val bannerUrl: String,
    @SerializedName("brand_id")
    val brandid: String,
)