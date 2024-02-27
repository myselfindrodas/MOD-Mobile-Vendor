package com.example.modmobilesmartsale.data.model.dashboardmodel

import com.google.gson.annotations.SerializedName

data class DashboardRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("user_id")
    val user_id: String,
    @SerializedName("user_type_app")
    val user_type_app: String,
)