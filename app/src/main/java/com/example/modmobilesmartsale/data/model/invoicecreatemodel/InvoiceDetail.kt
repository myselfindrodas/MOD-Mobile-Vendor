package com.example.modmobilesmartsale.data.model.invoicecreatemodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class InvoiceDetail(
    @SerializedName("cgst_amount")
    val cgstAmount: String,
    @SerializedName("cgst_per")
    val cgstPer: String,
    @SerializedName("disccount")
    val disccount: String,
    @SerializedName("hsn_code")
    val hsnCode: String,
    @SerializedName("igst_amount")
    val igstAmount: String,
    @SerializedName("igst_per")
    val igstPer: String,
    @SerializedName("imei1")
    val imei1: String,
    @SerializedName("imei2")
    val imei2: String,
    @SerializedName("item_category")
    val itemCategory: String,
    @SerializedName("item_total")
    val itemTotal: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("purchase_price")
    val purchasePrice: String,
    @SerializedName("qty")
    val qty: String,
    @SerializedName("sgst_amount")
    val sgstAmount: String,
    @SerializedName("sgst_per")
    val sgstPer: String,
    @SerializedName("taxable_value")
    val taxableValue: Double,
    @SerializedName("type")
    val type: String
):Serializable