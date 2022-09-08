package com.example.e_learning.data.todoList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class ToDoList (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val tanggal : String,
    val tanggalDeadline : String,
    val judul: String,
    val pesan : String,
    val status : Boolean
)
//