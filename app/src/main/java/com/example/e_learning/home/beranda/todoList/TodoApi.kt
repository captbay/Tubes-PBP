package com.example.e_learning.home.beranda.todoList

class TodoApi {
    companion object{
        val BASE_URL = "https://tubespbp.akordmusic.com/"

        val GET_ALL_URL = BASE_URL + "todolists"
        val GET_BY_ID_URL = BASE_URL + "todolists/"
        val DELETE_URL = BASE_URL + "todolists/"
        val UPDATE_URL = BASE_URL + "todolists/"
        val ADD_URL = BASE_URL + "todolists"
    }
}