package com.example.modmobilesmartsale.data.model.storagecolormodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("color")
    val color: String,
    @SerializedName("color_code")
    val color_code: String,
    @SerializedName("image1")
    val image1: String,
    @SerializedName("image2")
    val image2: String,
    @SerializedName("memory")
    val memory: String,
    @SerializedName("sno")
    val sno: Int,
    var isSelected: Boolean= false

)