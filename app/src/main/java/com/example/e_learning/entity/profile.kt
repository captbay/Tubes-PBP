package com.example.e_learning.entity


//masih dalam pengembangan
class profile (var username:String, var password:String, var email:String, var tglLahir:String, var noTelp:String ) {

    companion object{
        var listOfProfile = arrayOf(
            profile("Ages","dude","dude@gmail.com","29/12/01","618231231231")
        )
    }
}