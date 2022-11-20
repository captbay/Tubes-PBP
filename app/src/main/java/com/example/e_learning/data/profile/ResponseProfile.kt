package com.example.e_learning.data.profile

import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("status") val stt:String,
    val count : Int,
    val data :Profile
)
