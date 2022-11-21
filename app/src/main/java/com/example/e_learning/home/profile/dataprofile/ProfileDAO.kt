package com.example.e_learning.home.profile.dataprofile

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
    @Query("SELECT * From profile_table")
    suspend fun getProfile()    : List<Profile>

    @Query("SELECT * FROM profile_table Where id =:user_id")
    suspend fun getProfile(user_id: Int) : List<Profile>

}