package com.example.modmobilesmartsale.data.model.invoicecreatemodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BillJSON(
    @SerializedName("company_gstno")
    val companyGstno: String,
    @SerializedName("crm_party_code")
    val crmPartyCode: String,
    @SerializedName("customer_gstno")
    val customerGstno: String,
    @SerializedName("invoice_date")
    val invoiceDate: String,
    @SerializedName("invoice_details")
    val invoiceDetails: List<InvoiceDetail>,
    @SerializedName("invoice_no")
    val invoiceNo: String,
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
    @SerializedName("ship_from_addrs")
    val shipFromAddrs: String,
    @SerializedName("ship_from_state")
    val shipFromState: String,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("shipping_city")
    val shippingCity: String,
    @SerializedName("shipping_state")
    val shippingState: String,
    @SerializedName("shipping_zip")
    val shippingZip: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("tot_amount")
    val totAmount: String,
    @SerializedName("tot_cgst_amt")
    val totCgstAmt: String,
    @SerializedName("tot_discount_amt")
    val totDiscountAmt: String,
    @SerializedName("tot_igst_amt")
    val totIgstAmt: String,
    @SerializedName("tot_qty")
    val totQty: String,
    @SerializedName("tot_sgst_amt")
    val totSgstAmt: String,
    @SerializedName("tot_taxable_amt")
    val totTaxableAmt: String
):Serializable