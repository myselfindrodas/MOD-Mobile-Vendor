package com.example.modmobilesmartsale.data.model.pincodemodel


import com.google.gson.annotations.SerializedName

data class PincodeRequest(
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("token")
    val token: String
)