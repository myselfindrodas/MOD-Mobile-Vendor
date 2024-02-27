package com.example.modmobilesmartsale.data.model.myoderlistmodel


import com.google.gson.annotations.SerializedName

data class OrderListRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("challan_no")
    val challan_no: String
)