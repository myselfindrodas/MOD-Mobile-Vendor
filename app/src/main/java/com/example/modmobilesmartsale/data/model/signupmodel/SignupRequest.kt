package com.example.modmobilesmartsale.data.model.signupmodel


import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("address_1")
    val address1: String,
    @SerializedName("address_2")
    val address2: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("create_date")
    val createDate: String,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pancard")
    val pancard: String,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("token")
    val token: String
)