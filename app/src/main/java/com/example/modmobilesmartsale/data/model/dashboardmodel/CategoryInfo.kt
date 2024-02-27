package com.example.modmobilesmartsale.data.model.dashboardmodel


import com.google.gson.annotations.SerializedName

data class CategoryInfo(
    @SerializedName("grade_info")
    val gradeInfo: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("imei_box")
    val imeiBox: String,
    @SerializedName("qty_inventory")
    val qtyInventory: String,
    @SerializedName("stock_id")
    val stockId: String,
    @SerializedName("stock_name")
    val stockName: String
)