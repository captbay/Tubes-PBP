package com.example.e_learning.home.profile.dataprofile

import androidx.room.Entity
import androidx.room.PrimaryKey

//class Profile (var username: String, var password: String, var email: String, var tglLahir: String, var noTelp: String) {
//    var id: Long? =null
//}

@Entity(tableName = "profile_table")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val username : String,
    val password : String,
    val email : String,
    val tglLahir : String,
    val noTelp : String
)

