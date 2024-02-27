package com.example.modmobilesmartsale.data.model.viewcartmodel


import com.google.gson.annotations.SerializedName

data class IsFavItem(
    @SerializedName("current_field")
    val currentField: Any?,
    @SerializedName("field_count")
    val fieldCount: Any?,
    @SerializedName("lengths")
    val lengths: Any?,
    @SerializedName("num_rows")
    val numRows: Any?,
    @SerializedName("type")
    val type: Any?
)