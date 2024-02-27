package com.example.modmobilesmartsale.data.model.manageaddressmodel


import com.google.gson.annotations.SerializedName

data class ManageAddressRequest(
    @SerializedName("addressid")
    val addressid: String,
    @SerializedName("addrs_type")
    val addrsType: String,
    @SerializedName("alternate_no")
    val alternateNo: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("contact_name")
    val contactName: String,
    @SerializedName("contact_no")
    val contactNo: String,
    @SerializedName("landmark")
    val landmark: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("reqtype")
    val reqtype: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userid")
    val userid: String
)