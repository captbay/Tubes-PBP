package com.example.e_learning.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val username : String,
    val password : String,
    val email : String,
    val tglLahir : String,
    val noTelp : String
)