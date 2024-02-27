package com.example.modmobilesmartsale.data.model.dashboardmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bannerData")
    val bannerData: List<BannerData>,
    @SerializedName("cartCount")
    val cartCount: Any?,
    @SerializedName("category_info")
    val categoryInfo: List<CategoryInfo>,
    @SerializedName("latest_edition_img")
    val latestEditionImg: List<LatestEditionImg>,
    @SerializedName("recommendItemsData")
    val recommendItemsData: List<Any>,
    @SerializedName("seller_code")
    val sellerCode: Boolean,
    @SerializedName("Shop_by_budget_img")
    val shopByBudgetImg: List<ShopByBudgetImg>,
    @SerializedName("top_brands")
    val topBrands: List<TopBrand>,
    @SerializedName("UserId")
    val userId: Boolean
)