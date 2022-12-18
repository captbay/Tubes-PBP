package com.example.e_learning.home.beranda.todoList

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("status") val stt:String,
    val count : Int,
    val data :List<ToDoList>
)
