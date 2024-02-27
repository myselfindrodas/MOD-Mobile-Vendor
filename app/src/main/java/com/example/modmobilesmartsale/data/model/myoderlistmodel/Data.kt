package com.example.modmobilesmartsale.data.model.myoderlistmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("cgst_amount")
    val cgstAmount: String,
    @SerializedName("cgst_per")
    val cgstPer: String,
    @SerializedName("challan_no")
    val challanNo: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("mrp")
    val mrp: String,
    @SerializedName("delivery_time")
    val deliveryTime: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("for_cancel")
    val forCancel: String,
    @SerializedName("for_return")
    val forReturn: String,
    @SerializedName("igst_amount")
    val igstAmount: String,
    @SerializedName("igst_per")
    val igstPer: String,
    @SerializedName("image1")
    val image1: String?,
    @SerializedName("imei1")
    val imei1: String,
    @SerializedName("imei2")
    val imei2: String,
    @SerializedName("invoice_no")
    val invoiceNo: String,
    @SerializedName("item_total")
    val itemTotal: String,
    @SerializedName("memory")
    val memory: String,
    @SerializedName("model_code")
    val modelCode: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("purchase_price")
    val purchasePrice: String,
    @SerializedName("Qty")
    val qty: String,
    @SerializedName("sale_date")
    val saleDate: String?,
    @SerializedName("sgst_amount")
    val sgstAmount: String,
    @SerializedName("sgst_per")
    val sgstPer: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("stock_type")
    val stockType: String?,
    @SerializedName("taxable_value")
    val taxableValue: String,
    @SerializedName("working_hour")
    val working_hour: String,
    @SerializedName("date_time")
    val datetime: String
):Serializable