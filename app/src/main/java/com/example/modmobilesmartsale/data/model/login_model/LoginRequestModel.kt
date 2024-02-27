package com.example.modmobilesmartsale.data.model.login_model

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("user_id")
    val user_id: String,
    @SerializedName("token")
    val token: String,
)