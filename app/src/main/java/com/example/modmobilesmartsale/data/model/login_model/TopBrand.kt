package com.example.modmobilesmartsale.data.model.login_model


import com.google.gson.annotations.SerializedName

data class TopBrand(
    @SerializedName("brand_id")
    val brandId: String,
    @SerializedName("img_url")
    val imgUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String
)