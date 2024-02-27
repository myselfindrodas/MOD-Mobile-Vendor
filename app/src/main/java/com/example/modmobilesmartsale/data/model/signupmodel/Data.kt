package com.example.modmobilesmartsale.data.model.signupmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("client_id")
    val clientId: Any?,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("party_code")
    val partyCode: String,
    @SerializedName("party_type")
    val partyType: String
)