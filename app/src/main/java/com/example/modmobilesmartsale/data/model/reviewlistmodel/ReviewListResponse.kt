package com.example.modmobilesmartsale.data.model.reviewlistmodel


import com.example.modmobilesmartsale.data.model.reviewlistmodel.Response
import com.google.gson.annotations.SerializedName

data class ReviewListResponse(
    @SerializedName("response")
    val response: Response
)