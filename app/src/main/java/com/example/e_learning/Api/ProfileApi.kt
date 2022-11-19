package com.example.e_learning.Api

class ProfileApi {
    companion object {
        val BASE_URL = "https://elearning-pbp.herokuapp.com/users"

        val GET_ALL_URL = BASE_URL + "profile/"
        val GET_BY_ID_URL = BASE_URL + "profile/"
        val ADD_URL = BASE_URL + "profile/"
        val UPDATE_URL = BASE_URL + "profile/"
        val DELETE_URL = BASE_URL + "profile/"
    }
}