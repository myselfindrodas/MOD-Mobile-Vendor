package com.example.modmobilesmartsale.data.model.invoicecancelmodel


import com.google.gson.annotations.SerializedName

data class InvoiceCancelRequest(
    @SerializedName("challan_no")
    val challanNo: String,
    @SerializedName("remark")
    val remark: String,
    @SerializedName("token")
    val token: String
)