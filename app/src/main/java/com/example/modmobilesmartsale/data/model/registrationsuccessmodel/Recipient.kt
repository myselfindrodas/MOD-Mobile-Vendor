package com.example.modmobilesmartsale.data.model.registrationsuccessmodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Recipient(
    @SerializedName("mobiles")
    val mobiles: String,
    @SerializedName("VAR1")
    val vAR1: String,
    @SerializedName("VAR2")
    val vAR2: String
):Serializable