package com.example.modmobilesmartsale.data.model.getimagemodel


import com.google.gson.annotations.SerializedName

data class ImaUrl(
    @SerializedName("image1")
    val image1: String,
    @SerializedName("image2")
    val image2: String,
    @SerializedName("image3")
    val image3: String,
    @SerializedName("image4")
    val image4: String
)