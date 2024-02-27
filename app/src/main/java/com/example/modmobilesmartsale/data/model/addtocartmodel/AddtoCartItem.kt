package com.example.modmobilesmartsale.data.model.addtocartmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddtoCartItem(
    @SerializedName("cod_charges")
    val codCharges: String,
    @SerializedName("model_code")
    val modelCode: String,
    @SerializedName("payment_mode")
    val paymentMode: String,
    @SerializedName("payment_remark")
    val paymentRemark: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("productid")
    val productid: String,
    @SerializedName("purchase_price")
    val purchasePrice: String,
    @SerializedName("qty")
    val qty: String,
    @SerializedName("reqtype")
    val reqtype: String,
    @SerializedName("seller_cod")
    val sellerCod: String,
    @SerializedName("sessionid")
    val sessionid: String,
    @SerializedName("stock_type")
    val stockType: String,
    @SerializedName("userid")
    val userid: String
):Serializable