package com.example.e_learning.home.beranda.spama

class SpamasApi {
    companion object{
        val BASE_URL = "https://tubespbp.akordmusic.com/"

        val GET_ALL_URL = BASE_URL + "spamas"
        val GET_BY_ID_URL = BASE_URL + "spamas/"
        val DELETE_URL = BASE_URL + "spamas/"
        val UPDATE_URL = BASE_URL + "spamas/"
        val ADD_URL = BASE_URL + "spamas"
    }
}