package com.example.modmobilesmartsale.data.model.pocreatemodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class POJSON(
    @SerializedName("cod_charges")
    val codCharges: String,
    @SerializedName("crm_party_code")
    val crmPartyCode: String,
    @SerializedName("order_date")
    val orderDate: String,
    @SerializedName("order_details")
    val orderDetails: List<OrderDetail>,
    @SerializedName("order_no")
    val orderNo: String,
    @SerializedName("party_email")
    val partyEmail: String,
    @SerializedName("party_mobile")
    val partyMobile: String,
    @SerializedName("party_name")
    val partyName: String,
    @SerializedName("payment_mode")
    val paymentMode: String,
    @SerializedName("payment_remark")
    val paymentRemark: String,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("shipping_city")
    val shippingCity: String,
    @SerializedName("shipping_state")
    val shippingState: String,
    @SerializedName("shipping_zip")
    val shippingZip: String,
    @SerializedName("tot_amount")
    val totAmount: String,
    @SerializedName("tot_qnty")
    val totQnty: String
):Serializable