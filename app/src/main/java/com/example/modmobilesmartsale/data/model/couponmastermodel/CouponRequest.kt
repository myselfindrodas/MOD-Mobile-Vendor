package com.example.modmobilesmartsale.data.model.couponmastermodel


import com.google.gson.annotations.SerializedName

data class CouponRequest(
    @SerializedName("coupon_code")
    val couponCode: String,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("token")
    val token: String
)