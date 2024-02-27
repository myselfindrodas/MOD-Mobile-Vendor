package com.example.modmobilesmartsale.data.model.viewaddressmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Addressdata(
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
    @SerializedName("state")
    val state: String,
    @SerializedName("default_type")
    val default_type: String,
    var isPrimary: Boolean,
):Serializable