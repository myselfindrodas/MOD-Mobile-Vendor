package com.example.modmobilesmartsale.data.model.viewcartmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cart_count")
    val cartCount: Int,
    @SerializedName("cartdata")
    val cartdata: List<Cartdata>,
    @SerializedName("company_gstno")
    val companyGstno: String,
    @SerializedName("crm_party_cod")
    val crmPartyCod: String,
    @SerializedName("customer_gstno")
    val customerGstno: String,
    @SerializedName("discAmt")
    val discAmt: String,
    @SerializedName("estidelvry")
    val estidelvry: String,
    @SerializedName("parentCity")
    val parentCity: String,
    @SerializedName("parentName")
    val parentName: String,
    @SerializedName("parentState")
    val parentState: String,
    @SerializedName("party_email")
    val partyEmail: String,
    @SerializedName("party_mobil")
    val partyMobil: String,
    @SerializedName("seller_address")
    val sellerAddress: String,
    @SerializedName("seller_cod")
    val sellerCod: String,
    @SerializedName("seller_email")
    val sellerEmail: String,
    @SerializedName("seller_nam")
    val sellerNam: String,
    @SerializedName("seller_phone")
    val sellerPhone: String,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("shipping_zip")
    val shippingZip: String,
    @SerializedName("subAmt")
    val subAmt: String,
    @SerializedName("taxAmt")
    val taxAmt: String,
    @SerializedName("tot_cgst_amt")
    val totCgstAmt: String,
    @SerializedName("tot_igst_amt")
    val totIgstAmt: String,
    @SerializedName("tot_qnty")
    val totQnty: Int,
    @SerializedName("tot_sgst_amt")
    val totSgstAmt: String,
    @SerializedName("totTaxableAmt")
    val totTaxableAmt: String,
    @SerializedName("totalAmt")
    val totalAmt: String,
    @SerializedName("UserId")
    val userId: String,
    @SerializedName("wallet_amt")
    val walletAmt: String
)