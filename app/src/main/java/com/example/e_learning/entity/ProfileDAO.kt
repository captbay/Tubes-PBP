package com.example.e_learning.entity

import androidx.room.*


@Dao
interface ProfileDAO {
    //Create
    @Insert
    suspend fun addProfile(profile: Profile)

//Read


    //Update
    @Update
    suspend fun updateProfile(profile: Profile)

    //Delete -->
    @Delete
    suspend fun  deleteProfile(profile: Profile)

    //Query ntah untuk apa
    @Query("SELECT * From Profile")
    suspend fun getProfile()    : List<Profile>

    @Query("SELECT * FROM Profile Where username=:profile_username")
    suspend fun getProfile(profile_username: String) : List<Profile>

}