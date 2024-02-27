package com.example.modmobilesmartsale.data.model.qclistmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("name")
    val name: String,
    @SerializedName("rmk")
    val rmk: String,
    @SerializedName("sno")
    val sno: Int,
    @SerializedName("status")
    val status: String
)