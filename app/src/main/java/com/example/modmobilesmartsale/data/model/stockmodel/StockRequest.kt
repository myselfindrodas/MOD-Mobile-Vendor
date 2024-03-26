package com.example.modmobilesmartsale.data.model.stockmodel


import com.google.gson.annotations.SerializedName

data class StockRequest(
    @SerializedName("asc_code")
    val ascCode: String,
    @SerializedName("brand_id")
    val brandId: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("hot_deals")
    val hotDeal: String,
    @SerializedName("search")
    val search: String,
    @SerializedName("imei")
    val imei: String,
    @SerializedName("memory")
    val memory: String,
    @SerializedName("model_code")
    val modelCode: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("phone_version")
    val phoneVersion: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("stock_type")
    val stockType: String,
    @SerializedName("user_type_app")
    val user_type_app: String,
    @SerializedName("token")
    val token: String
)