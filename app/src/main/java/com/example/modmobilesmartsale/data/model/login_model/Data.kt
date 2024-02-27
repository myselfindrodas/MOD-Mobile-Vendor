package com.example.modmobilesmartsale.data.model.login_model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("bannerData")
    val bannerData: List<BannerData>,
    @SerializedName("cartCount")
    val cartCount: Any?,
    @SerializedName("category_info")
    val categoryInfo: List<CategoryInfo>,
    @SerializedName("city")
    val city: String,
    @SerializedName("gst_no")
    val gstNo: String,
    @SerializedName("latest_edition_img")
    val latestEditionImg: List<LatestEditionImg>,
    @SerializedName("recommendItemsData")
    val recommendItemsData: List<Any>,
    @SerializedName("seller_code")
    val sellerCode: Boolean,
    @SerializedName("Shop_by_budget_img")
    val shopByBudgetImg: List<ShopByBudgetImg>,
    @SerializedName("state")
    val state: String,
    @SerializedName("top_brands")
    val topBrands: List<TopBrand>,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("UserContact")
    val userContact: String,
    @SerializedName("UserEmail")
    val userEmail: String,
    @SerializedName("UserId")
    val userId: String,
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("user_type")
    val userType: String
)