package com.example.modmobilesmartsale.data.model.stockmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("apply_case")
    val applyCase: String,
    @SerializedName("battary")
    val battary: String,
    @SerializedName("box_original")
    val boxOriginal: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("brand_id")
    val brandid: String,
    @SerializedName("brand_warranty")
    val brandWarranty: String,
    @SerializedName("brand_warranty_till_date")
    val brandWarrantyTillDate: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("common_model")
    val commonModel: String,
    @SerializedName("Cust_Price")
    val custPrice: String,
    @SerializedName("deal_of_days")
    val dealOfDays: String,
    @SerializedName("discount_percentage_dist")
    val discountPercentageDist: Int,
    @SerializedName("discount_percentage_ret")
    val discountPercentageRet: Int,
    @SerializedName("display_original")
    val displayOriginal: String,
    @SerializedName("Dist_Price")
    val distPrice: String,
    @SerializedName("features")
    val features: String,
    @SerializedName("fm_radio")
    val fmRadio: String,
    @SerializedName("fornt_flash")
    val forntFlash: String,
    @SerializedName("fornt_senser")
    val forntSenser: String,
    @SerializedName("fornt_setup")
    val forntSetup: String,
    @SerializedName("front_camera_resolution")
    val frontCameraResolution: String,
    @SerializedName("front_video_recorder")
    val frontVideoRecorder: String,
    @SerializedName("imei1")
    val imei1: String,
    @SerializedName("internal_storage")
    val internalStorage: String,
    @SerializedName("iqc_image1")
    val iqcImage1: String,
    @SerializedName("iqc_image2")
    val iqcImage2: String,
    @SerializedName("iqc_pdf")
    val iqcPdf: String,
    @SerializedName("favourite")
    val favourite: String,
    @SerializedName("last_update")
    val lastUpdate: String,
    @SerializedName("MRP")
    val mRP: String,
    @SerializedName("memory")
    val memory: String,
    @SerializedName("memory_ram")
    val memoryRam: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("model_code")
    val modelCode: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("Original_image1")
    val originalImage1: String,
    @SerializedName("Original_image2")
    val originalImage2: String,
    @SerializedName("phone_version")
    val phoneVersion: String,
    @SerializedName("primary_camera")
    val primaryCamera: String,
    @SerializedName("purchase_price")
    val purchasePrice: String,
    @SerializedName("Ret_Price")
    val retPrice: String,
    @SerializedName("screen_size")
    val screenSize: String,
    @SerializedName("secondary_camera")
    val secondaryCamera: String,
    @SerializedName("sno")
    val sno: Int,
    @SerializedName("stock")
    val stock: String,
    @SerializedName("Stock_Qty")
    val stockQty: String,
    @SerializedName("Stock_Type")
    val stockType: String,
    @SerializedName("tax_type")
    val taxType: String,
    @SerializedName("wifi")
    val wifi: String,
    @SerializedName("wifi_features")
    val wifiFeatures: String

):Serializable