package com.example.e_learning.home.profile.dataprofile

import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("status") val stt:String,
    val count : Int,
    val data : Profile
)
