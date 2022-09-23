package com.example.e_learning.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = false)
//    val id : Int,
    val username : String,
    val password : String,
    val email : String,
    val tglLahir : String,
    val noTelp : String
)




////masih dalam pengembangan
//class profile (var username:String, var password:String, var email:String, var tglLahir:String, var noTelp:String ) {
//
//    companion object{
//        var listOfProfile = arrayOf(
//            profile("Ages","dude","dude@gmail.com","29/12/01","618231231231")
//        )
//    }
//}