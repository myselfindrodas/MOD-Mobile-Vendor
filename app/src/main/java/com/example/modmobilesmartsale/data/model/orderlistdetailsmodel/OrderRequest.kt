package com.example.modmobilesmartsale.data.model.orderlistdetailsmodel


import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("invoice_no")
    val invoiceNo: String,
    @SerializedName("token")
    val token: String
)