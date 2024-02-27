package com.example.modmobilesmartsale.data.model.pocreatemodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("app_order_no")
    val appOrderNo: String,
    @SerializedName("crm_po_no")
    val crmPoNo: String,
    @SerializedName("err_flag")
    val errFlag: Int,
    @SerializedName("po_number")
    val poNumber: String
)