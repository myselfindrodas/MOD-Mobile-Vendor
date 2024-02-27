package com.example.modmobilesmartsale.data.model.viewcartmodel


import com.google.gson.annotations.SerializedName

data class Cartdata(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("cgst_amount")
    val cgstAmount: Double,
    @SerializedName("cgst_per")
    val cgstPer: Int,
    @SerializedName("cod_charges")
    val codCharges: String,
    @SerializedName("dealerprice")
    val dealerprice: String,
    @SerializedName("hsn_code")
    val hsnCode: String,
    @SerializedName("igst_amount")
    val igstAmount: Int,
    @SerializedName("igst_per")
    val igstPer: Int,
    @SerializedName("isFavItem")
    val isFavItem: IsFavItem,
    @SerializedName("isStock")
    val isStock: String,
    @SerializedName("item_category")
    val itemCategory: String,
    @SerializedName("item_total")
    val itemTotal: String,
    @SerializedName("model_code")
    val modelCode: String,
    @SerializedName("payment_mode")
    val paymentMode: String,
    @SerializedName("payment_remark")
    val paymentRemark: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("mrp")
    val mrp: String,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productImgUrl")
    val productImgUrl: Any?,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("productQty")
    val productQty: String,
    @SerializedName("purchase_price")
    val purchasePrice: String,
    @SerializedName("sgst_amount")
    val sgstAmount: Double,
    @SerializedName("sgst_per")
    val sgstPer: Int,
    @SerializedName("taxable_value")
    val taxableValue: String,
    @SerializedName("tax_type")
    val tax_type: String,
    @SerializedName("type")
    val type: String,
    var isSelected: Boolean= false

)