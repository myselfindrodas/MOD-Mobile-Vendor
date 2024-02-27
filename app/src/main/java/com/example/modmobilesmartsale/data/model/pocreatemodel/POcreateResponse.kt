package com.example.modmobilesmartsale.data.model.pocreatemodel


import com.google.gson.annotations.SerializedName

data class POcreateResponse(
    @SerializedName("app_order_no")
    val appOrderNo: String,
    @SerializedName("crm_po_no")
    val crmPoNo: String,
    @SerializedName("err_flag")
    val errFlag: Int,
    @SerializedName("po_number")
    val poNumber: String,
    @SerializedName("status")
    val status: String
)