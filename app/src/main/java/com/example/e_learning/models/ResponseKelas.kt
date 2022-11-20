package com.example.e_learning.models

import com.google.gson.annotations.SerializedName

data class ResponseKelas (
    @SerializedName("status") val stt:String,val totalData: Int, val data:List<Kelas>
)