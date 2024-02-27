package com.example.modmobilesmartsale.data.model.couponmastermodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("case_apply")
    val caseApply: String,
    @SerializedName("coupon_amt")
    val couponAmt: String,
    @SerializedName("coupon_code")
    val couponCode: String,
    @SerializedName("coupon_max_amt")
    val couponMaxAmt: String,
    @SerializedName("coupon_min_amt")
    val couponMinAmt: String,
    @SerializedName("coupon_percentage")
    val couponPercentage: String,
    @SerializedName("stock_cat")
    val stockCat: String,
    @SerializedName("type")
    val type: String
)