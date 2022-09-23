package com.example.e_learning.entity

import androidx.room.*


@Dao
interface PresensiDAO {

    @Insert
    suspend fun addPresensi(presensi: Presensi)

    @Update
    suspend fun updatePresensi(presensi: Presensi)

    @Delete
    suspend fun deletePresensi(presensi: Presensi)

    @Query("SELECT * FROM Presensi")
    suspend fun getPresensi() : List<Presensi>

    @Query("SELECT * FROM Presensi Where id =:presensi_id")
    suspend fun getPresensi(presensi_id : Int) : List<Presensi>
}