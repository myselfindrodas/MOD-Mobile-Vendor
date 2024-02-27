package com.example.modmobilesmartsale.data.model.invoicecreatemodel


import com.google.gson.annotations.SerializedName

data class InvoicecreateResponse(
    @SerializedName("app_order_no")
    val appOrderNo: String,
    @SerializedName("crm_invoice_no")
    val crmInvoiceNo: String,
    @SerializedName("err_flag")
    val errFlag: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: String
)