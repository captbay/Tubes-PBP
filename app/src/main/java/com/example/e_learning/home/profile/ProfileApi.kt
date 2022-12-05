package com.example.e_learning.home.profile

class ProfileApi {
    companion object {
        val BASE_URL = "https://tubespbp.akordmusic.com/"

        val GET_ALL_URL = BASE_URL + "users"
        val GET_BY_ID_URL = BASE_URL + "users/"
        val ADD_URL = BASE_URL + "users"
        val UPDATE_URL = BASE_URL + "users/"
        val DELETE_URL = BASE_URL + "users/"
        val LOGIN_URL = BASE_URL + "login"
        val REGISTER_URL = BASE_URL + "register"
    }
}