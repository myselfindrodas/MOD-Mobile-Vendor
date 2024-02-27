package com.example.modmobilesmartsale.data.model.storagecolormodel


import com.google.gson.annotations.SerializedName

data class StorageColorRequest(
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("token")
    val token: String
)