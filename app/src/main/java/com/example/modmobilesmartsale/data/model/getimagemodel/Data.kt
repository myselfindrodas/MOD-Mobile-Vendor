package com.example.modmobilesmartsale.data.model.getimagemodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ima_url")
    val imaUrl: List<ImaUrl>,
    @SerializedName("status")
    val status: String
)