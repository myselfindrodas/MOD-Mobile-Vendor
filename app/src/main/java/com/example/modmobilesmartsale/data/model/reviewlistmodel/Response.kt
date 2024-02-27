package com.example.modmobilesmartsale.data.model.reviewlistmodel


import com.example.modmobilesmartsale.data.model.reviewlistmodel.Data
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)