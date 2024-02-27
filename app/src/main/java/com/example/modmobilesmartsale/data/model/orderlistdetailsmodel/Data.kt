package com.example.modmobilesmartsale.data.model.orderlistdetailsmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cancel_date")
    val cancelDate: String,
    @SerializedName("cancel_rmk")
    val cancelRmk: String,
    @SerializedName("courier")
    val courier: String,
    @SerializedName("customer_address")
    val customerAddress: String,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("dc_time")
    val dcTime: String,
    @SerializedName("dispatch_date")
    val dispatchDate: String,
    @SerializedName("docket_no")
    val docketNo: String,
    @SerializedName("invoic_date")
    val invoicDate: String,
    @SerializedName("invoic_time")
    val invoicTime: String,
    @SerializedName("invoice_no")
    val invoiceNo: String,
    @SerializedName("order_date")
    val orderDate: Any?,
    @SerializedName("order_no")
    val orderNo: String,
    @SerializedName("status")
    val status: String
)