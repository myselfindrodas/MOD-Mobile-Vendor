package com.example.modmobilesmartsale.data.model.reviewlistmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("flag")
    val flag: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("prodid")
    val prodid: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("userid")
    val userid: String
)