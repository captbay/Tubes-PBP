package com.example.e_learning.home.kelas.data

import com.google.gson.annotations.SerializedName

data class ResponseKelas (
    @SerializedName("status") val stt:String,val totalData: Int, val data:List<Kelas>
)