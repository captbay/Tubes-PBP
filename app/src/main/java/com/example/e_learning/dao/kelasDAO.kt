package com.example.e_learning.dao


import androidx.room.*
import com.example.e_learning.entity.Kelas


interface kelasDAO {
    //Create
    @Insert
    suspend fun addProfile(kelas: Kelas)

//Read


    //Update
    @Update
    suspend fun updateProfile(kelas: Kelas)

    //Delete -->
    @Delete
    suspend fun  deleteProfile(kelas: Kelas)

    //Query ntah untuk apa
    @Query("SELECT * From Kelas")
    suspend fun getProfile()    : List<Kelas>

//    Ini gimana
//    @Query("SELECT * FROM Profile Where username=:profile_username")
//    suspend fun getProfile(profile_username: String) : List<Kelas>
}