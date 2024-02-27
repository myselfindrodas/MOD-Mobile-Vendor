package com.example.modmobilesmartsale.data.model.invoicecancelmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("crm_invoice_no")
    val crmInvoiceNo: String,
    @SerializedName("err_flag")
    val errFlag: Int,
    @SerializedName("msg")
    val msg: String
)