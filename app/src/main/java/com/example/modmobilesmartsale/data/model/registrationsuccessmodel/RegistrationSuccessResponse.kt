package com.example.modmobilesmartsale.data.model.registrationsuccessmodel


import com.google.gson.annotations.SerializedName

data class RegistrationSuccessResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String
)