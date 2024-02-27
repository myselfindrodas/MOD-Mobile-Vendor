package com.example.modmobilesmartsale.data.model.pocreatemodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDetail(
    @SerializedName("item_category")
    val itemCategory: String,
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("item_price")
    val itemPrice: String,
    @SerializedName("qnty")
    val qnty: String,
    @SerializedName("sku_id")
    val skuId: String
):Serializable