package com.example.e_learning.Data.ToDoList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class ToDoList (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val tanggal : String,
    val tanggalDeadline : String,
    val judul: String,
    val pesan : String, //tipe kehadiran H, I, S
    val status : Boolean
)
//