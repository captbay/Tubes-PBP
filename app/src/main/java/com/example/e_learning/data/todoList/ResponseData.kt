package com.example.e_learning.data.todoList

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("status") val stt:String,
    val count : Int,
    val data :List<ToDoList>
)
